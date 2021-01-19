package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import model.entities.Collaborator;
import model.entities.Project;
import model.entities.Publication;
import model.entities.Student;
import model.enums.StatusProject;
import model.enums.TypeStudent;
import model.exceptions.DomainException;
import views.Menu;
import views.Utility;

public class ControllerProject {
	
	private static Scanner sc = new Scanner(System.in);
	private static Random rd = new Random();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private ArrayList<Project> projects = new ArrayList<Project>();
	
	private static ControllerProject instance = null;
	
	private ControllerProject() {
	}

	public static ControllerProject getInstance() {
		if(instance == null) {
			instance = new ControllerProject();
		}
		return instance;
	}
		
	public ArrayList<Project> getProjects() {
		return projects;
	}

	public Project createProject() throws DomainException, ParseException {
		System.out.print("Título: ");
		String title = sc.nextLine();
		System.out.print("Data de início (dd/MM/yyyy): ");
		Date start = sdf.parse(sc.nextLine());
		System.out.print("Data de término (dd/MM/yyyy): ");
		Date finish = sdf.parse(sc.nextLine());
		System.out.print("Agência financiadora: ");
		String agency = sc.nextLine();
		System.out.print("Valor financiado: ");
		double amount = Double.parseDouble(sc.nextLine());
		System.out.print("Objetivo: ");
		String objective = sc.nextLine();
		System.out.print("Descrição: ");
		String description = sc.nextLine();
		int id = rd.nextInt(1000);
		while(haveId(id)) {
			id = rd.nextInt();
		}
		Project project = new Project(id, title, start, finish, agency, amount, objective, description, StatusProject.IN_PREPARATION);
		return project;
	}

	public void register(){
		try {
			Project project = createProject();
			projects.add(project);
			System.out.println(project);
			System.out.println("\nO projeto foi cadastrado com sucesso!");
		}
		catch(DomainException e) {
			System.out.println("\nErro: " + e.getMessage());
		}
		catch(ParseException e) {
			System.out.println("\nErro: Formato de data inválido.");
		}
		catch(NumberFormatException e) {
			System.out.println("\nErro: Entrada inválida.");
		}
		finally {
			Utility.enter();
		}
	}
	
	public void editProjectInformation(){
		try {
			print();
			System.out.print("Informe o id do projeto: ");
			int id = Integer.parseInt(sc.nextLine());
			checkId(id);
			Project project = findProject(id);
			Project editedProject = createProject();
			editedProject.setId(id);
			editedProject.setStatus(project.getStatus());
			projects.set(indexProject(project), editedProject);
			System.out.println(editedProject);
			System.out.println("\nOs dados do projeto foram alterados.");
		}
		catch(DomainException e) {
			System.out.println("\nErro: " + e.getMessage());
		}
		catch(ParseException e) {
			System.out.println("\nErro: Formato de data inválido.");
		}
		catch(NumberFormatException e) {
			System.out.println("\nErro: Entrada inválida.");
		}
		finally {
			Utility.enter();
		}
	}
	
	public void statusChange() {
		try {
			print();
			System.out.print("Id do projeto: ");
			int id = Integer.parseInt(sc.nextLine());
			checkId(id);
			Project project = findProject(id);
			System.out.println("\nStatus atual: " + project.getStatus().getStatusProject());
			Menu.showMenuStatus();
			int type = Integer.parseInt(sc.nextLine());
			checkType(type);
			switch(type) {
				case 1:
					checkInformation(project);
					if(project.getStatus() == StatusProject.IN_PREPARATION) {
						studentMore2InProgress(project);
						project.setStatus(StatusProject.IN_PROCESS);
						System.out.println("\nNovo status: " + project.getStatus().getStatusProject());
					}
					else if(project.getStatus() == StatusProject.IN_PROCESS) {
						if(project.getPublications().size() > 0) {
							project.setStatus(StatusProject.CONCLUDED);
							System.out.println("\nNovo status: " + project.getStatus().getStatusProject());
						}else {
							System.out.println("\nNão é possível alterar status do projeto para \"Concluído\". "
									+ "\nAssocie pelo menos uma publicação ao projeto.");
						}
					}	
					break;
				case 0:
					System.out.println("O programa irá retornar para o menu inicial.");
					break;
			}

		}
		catch(DomainException e) {
			System.out.println("\nErro: " + e.getMessage());
		}
		catch(NumberFormatException e) {
			System.out.println("\nErro: Entrada inválida.");
		}
		finally {
			Utility.enter();
		}
	}
	
	public void allocationOfParticipants(ControllerCollaborator controllerCollaborator) {
		try {
			print();
			controllerCollaborator.print();
			System.out.print("Id do projeto: ");
			int id = Integer.parseInt(sc.nextLine());
			checkId(id);
			System.out.print("Id do colaborador a ser alocado: ");
			int idc = Integer.parseInt(sc.nextLine());
			controllerCollaborator.checkId(idc);
			Project project = findProject(id);
			checkInPreparation(project);
			Collaborator collaborator = controllerCollaborator.findCollaborator(idc);
			if(haveCollarator(project, idc)) {
				System.out.println("\nColaborador já está alocado.");
			}else {
				if(project.getCollaborators().isEmpty()) {
					controllerCollaborator.isTeacher(collaborator);
					project.addCollaborator(collaborator);	
					collaborator.addProject(project);
					System.out.println("\nColaborador foi alocado no projeto.");
				}
				else {
					project.addCollaborator(collaborator);
					collaborator.addProject(project);
					System.out.println("\nColaborador foi alocado no projeto.");
				}
			}		
		}
		catch(DomainException e) {
			System.out.println("\nErro: " + e.getMessage());
		}
		catch(NumberFormatException e) {
			System.out.println("\nErro: Entrada inválida.");
		}
		finally {
			Utility.enter();
		}
	}
	
	public void associatePublication(ControllerAcademicProduction controllerAcademicProduction) {
		try {
			print();
			controllerAcademicProduction.printPublications();
			System.out.print("Id do projeto: ");
			int id = Integer.parseInt(sc.nextLine());
			checkId(id);
			System.out.print("Id da publicação: ");
			int idP = Integer.parseInt(sc.nextLine());
			controllerAcademicProduction.checkIdPublication(idP);
			Project project = findProject(id);
			if(havePublication(project, idP)) {
				System.out.println("\nA publicação já está associada ao projeto.");
			}else {
				Publication publication = controllerAcademicProduction.findPublication(idP);
				project.addPublication(publication);
				publication.setProject(project);
				System.out.println("\nA publicação foi associada ao projeto.");
			}	
		}
		catch(DomainException e) {
			System.out.println("\nErro: " + e.getMessage());
		}
		catch(NumberFormatException e) {
			System.out.println("\nErro: Entrada inválida.");
		}
		finally {
			Utility.enter();
		}
	}
	
	public void consultProject() {
		try {
			print();
			System.out.print("Id do projeto: ");
			int id = Integer.parseInt(sc.nextLine());
			checkId(id);
			Project project = findProject(id);
			SortByDate.sortPublication(project.getPublications());
			System.out.println(project + "\n");
			printInConsultCollaborator(project);
			printInConsultPublication(project);
		}
		catch(DomainException e) {
			System.out.println("\nErro: " + e.getMessage());
		}
		catch(NumberFormatException e) {
			System.out.println("\nErro: Entrada inválida.");
		}
		finally {
			Utility.enter();
		}
	}
	
	public void printInConsultCollaborator(Project project) {
		if(project.getCollaborators().size()>0) {
			int k=1;
			System.out.println("------------------------------------");
			for(int i=0; i<project.getCollaborators().size(); i++) {
				System.out.print("Colaborador #" + k);
				System.out.println(project.getCollaborators().get(i) + "\n");
				k++;
			}
			System.out.println("------------------------------------");
		}else {
			System.out.println("------------------------------------");
			System.out.println("Sem colaboradores.");
			System.out.println("------------------------------------");
		}
	}
	
	public void printInConsultPublication(Project project) {
		if(project.getPublications().size()>0) {
			int l=1;
			System.out.println("------------------------------------");
			for(int i=0; i<project.getPublications().size(); i++) {
				System.out.print("Publicação #" + l);
				System.out.println(project.getPublications().get(i) + "\n");
				l++;
			}
			System.out.println("------------------------------------");
		}else {
			System.out.println("------------------------------------");
			System.out.println("Sem publicações.");
			System.out.println("------------------------------------");
		}
	}
	
	public void removeCollaborator(ControllerCollaborator controllerCollaborator) {
		try {
			print();
			controllerCollaborator.print();
			System.out.print("Id do projeto: ");
			int id = Integer.parseInt(sc.nextLine());
			checkId(id);
			System.out.print("Id do colaborador a ser removido: ");
			int idC = Integer.parseInt(sc.nextLine());
			controllerCollaborator.checkId(idC);
			Project project = findProject(id);
			Collaborator collaborator = controllerCollaborator.findCollaborator(idC);
			project.removeCollaborator(collaborator);
			collaborator.removeProject(project);
			System.out.println("\nColaborador foi removido.");
		}
		catch(DomainException e) {
			System.out.println("\nErro: " + e.getMessage());
		}
		catch(NumberFormatException e) {
			System.out.println("\nErro: Entrada inválida.");
		}
		finally {
			Utility.enter();
		}
	}
	
	public boolean studentMore2InProgress(Project project) throws DomainException {
		for(Collaborator c : project.getCollaborators()) {
			if(c instanceof Student) {
				Student student = (Student)c;
				if(student.getTypeStudent() == TypeStudent.GRADUATE_STUDENT) {
					if(haveMore2(student) == 2) {
						throw new DomainException("O estudante do Id:" + IdStudentMore2InProgress(project) + " possui 2 projetos \"Em andamento\"."
								+ "\nPara alterar o status do projeto atual é necessário que remova o estudante especificado.");
					}
				}
			}
		}
		return false;
	}
	
	public int IdStudentMore2InProgress(Project project) {
		for(Collaborator c : project.getCollaborators()) {
			if(c instanceof Student) {
				Student student = (Student)c;
				if(student.getTypeStudent() == TypeStudent.GRADUATE_STUDENT) {
					if(haveMore2(student) == 2) {
						return student.getId();
					}
				}
			}
		}
		return 0;
	}
	
	public int haveMore2(Student student) {
		int i=0;
		for(Project j : student.getProject()) {
			if(j.getStatus() == StatusProject.IN_PROCESS) {
				i++;	
			}
		}
		return i;
	}
	
	public boolean havePublication(Project project, int id) {
		for(Publication p : project.getPublications()) {
			if(p.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean haveCollarator(Project project, int id) {
		for(Collaborator c : project.getCollaborators()) {
			if(c.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public int numberOfInPreparation() {
		int in_preparation = 0;
		for(Project j : projects) {
			if(j.getStatus() == StatusProject.IN_PREPARATION) {
				in_preparation+=1;
			}
		}
		return in_preparation;
	}
	
	public int numberOfInProcess() {
		int in_process = 0;
		for(Project j : projects) {
			if(j.getStatus() == StatusProject.IN_PROCESS) {
				in_process+=1;
			}
		}
		return in_process;
	}
	
	public int numberOfConcluded() {
		int concluded = 0;
		for(Project j : projects) {
			if(j.getStatus() == StatusProject.CONCLUDED) {
				concluded+=1;
			}
		}
		return concluded;
	}
	
	public Project findProject(int id) {
		for(Project p : projects) {
	       if(p.getId() == id) {
	           return p;
	       }
		}
		return null;
	}
	
	public boolean haveId(int id){
		for(Project p : projects) {
			if(p.getId() == id) {
				return true;
		    }
		}
		return false;
	}
	
	public boolean checkInformation(Project project) throws DomainException {
		if(project.getFundingAgency().equals("") || project.getObjective().equals("") || project.getDescription().equals("")) {
			throw new DomainException("Informações básicas incompletas.");
		}
		return false;
		
	}
	
	public boolean checkId(int id) throws DomainException {
		for(Project p : projects) {
			if(p.getId() == id) {
				return true;
		    }
		}
		throw new DomainException("Id do projeto inválido.");
	}
	
	public boolean checkType(int type) throws DomainException {
		if(type == 0 || type == 1) {
			return true;
		}
		throw new DomainException("Opção inválida.");
	}
	
	public boolean checkInPreparation(Project project) throws DomainException {
		if(project.getStatus() == StatusProject.IN_PREPARATION) {
			return true;
		}		
		throw new DomainException("Não é possível adicionar colaboradores depois que o status do projeto é alterado para \"Em andamento\".");
	}
	
	public int indexProject(Project project) {
		int i = 0;
		for(Project p : projects) {
			if(p.equals(project)) {
				return i;
			}
			i++;
		}
		return 0;
	}
	
	public void print() {
		if(projects.size() > 0) {
			System.out.println("---------Projetos cadastrados-------");
			for(Project p : projects) {
				System.out.println("Id: " + p.getId() + "\nTítulo: " +p.getTitle());
				System.out.println("------------------------------------");
			}
		}
	}
	
}
