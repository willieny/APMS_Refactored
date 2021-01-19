package controllers;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import model.entities.Collaborator;
import model.entities.Student;
import model.entities.Teacher;
import model.enums.TypeStudent;
import model.exceptions.DomainException;
import views.Menu;
import views.Utility;

public class ControllerCollaborator {
	
	private static Scanner sc = new Scanner(System.in);
	private static Random rd = new Random();
	
	private ArrayList<Collaborator> collaborators = new ArrayList<Collaborator>();
	
	private static ControllerCollaborator instance = null;
	
	private ControllerCollaborator() {
	}	
	
	public static ControllerCollaborator getInstance() {
		if(instance == null) {
			instance = new ControllerCollaborator();
		}
		return instance;
	}
	
	public ArrayList<Collaborator> getCollaborators() {
		return collaborators;
	}

	public void register() {
		try {
			Menu.showMenuCollaborator();
			int type = Integer.parseInt(sc.nextLine());
			checkType(type);
			System.out.print("Nome: ");
			String name = sc.nextLine();
			System.out.print("e-mail: ");
			String email = sc.nextLine();
			int id = rd.nextInt(1000);
			while(haveId(id)) {
				id = rd.nextInt();
			}
			typeCollaborator(type, id, name, email);
			System.out.println(findCollaborator(id));
			System.out.println("\nColaborador foi cadastrado com sucesso!");
		}
		catch(DomainException e) {
			System.out.println("\nErro: Opção inválida.");
		}
		catch(NumberFormatException e) {
			System.out.println("\nErro: Opção inválida.");
		}
		finally {
			Utility.enter();
		}
	}
	
	public void typeCollaborator(int type, int id, String name, String email) {
		Collaborator collaborator = null;
		switch(type) {
			case 1:
				collaborator = new Teacher(id, name, email);
				break;
			case 2:
				collaborator = new Student(id, name, email, TypeStudent.GRADUATE_STUDENT);
				break;
			case 3:
				collaborator = new Student(id, name, email, TypeStudent.MASTER_STUDENT);
				break;
			case 4:
				collaborator = new Student(id, name, email, TypeStudent.PHD_STUDENT);
				break;
			case 5:
				collaborator = new Student(id, name, email, TypeStudent.RESEARCHER);
				break;
		}
		collaborators.add(collaborator);
	}

	public void consultCollaborator() {
		try {
			print();
			System.out.print("Id do colaborador: ");
			int id = Integer.parseInt(sc.nextLine());
			checkId(id);
			Collaborator collaborator = findCollaborator(id);
			SortByDate.sortProject(collaborator.getProject());
			SortByDate.sortPublication(collaborator.getPublications());
			System.out.println(collaborator + "\n");
			printInConsultCollaborator(collaborator);
			printInConsultPublication(collaborator);
			printInConsultOrientation(collaborator);
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
	
	public void printInConsultCollaborator(Collaborator collaborator) {
		if(collaborator.getProject().size() > 0) {
			int k=1;
			System.out.println("------------------------------------");
			for(int i=0; i<collaborator.getProject().size(); i++) {
				System.out.print("Projeto #" + k);
				System.out.println(collaborator.getProject().get(i) + "\n");
				k++;
			}
			System.out.println("------------------------------------");
		}else {
			System.out.println("------------------------------------");
			System.out.println("Sem projetos.");
			System.out.println("------------------------------------");
		}
	}
	
	public void printInConsultPublication(Collaborator collaborator) {
		if(collaborator.getPublications().size() > 0) {
			int l=1;
			System.out.println("------------------------------------");
			for(int i=0; i<collaborator.getPublications().size(); i++) {
				System.out.print("Publicação #" + l);
				System.out.println(collaborator.getPublications().get(i) + "\n");
				l++;
			}
			System.out.println("------------------------------------");
		}else {
			System.out.println("------------------------------------");
			System.out.println("Sem publicações.");
			System.out.println("------------------------------------");
		}
	}
	
	public void printInConsultOrientation(Collaborator collaborator) {
		if(collaborator instanceof Teacher) {
			Teacher teacher = (Teacher)collaborator;
			if(teacher.getOrientations().size() > 0) {
				int m=1;
				System.out.println("------------------------------------");
				for(int i=0; i<teacher.getOrientations().size(); i++) {
					System.out.print("Orientação #" + m);
					System.out.println(teacher.getOrientations().get(i) + "\n");
					m++;
				}
				System.out.println("------------------------------------");
			}else {
				System.out.println("------------------------------------");
				System.out.println("Sem orientações.");
				System.out.println("------------------------------------");
			}
		}
	}
	
	public Collaborator findTeacher(int id) {
		for(Collaborator c : collaborators) {
			 if (c instanceof Teacher) {
				 if(c.getId() == id) {
		               return c;
				}
             }
		}
		return null;
	}
	
	public Collaborator findCollaborator(int id) {
		for(Collaborator c : collaborators) {
			if(c.getId() == id) {
               return c;
			}
		}
		return null;
	}
	
	public boolean isTeacher(Collaborator collaborator) throws DomainException {
		if(collaborator instanceof Teacher) {
			return true;
		}
		throw new DomainException("O projeto não possui professores alocados. Adicione pelo menos um.");
	}
	
	public boolean checkIsTeacher(int id, ControllerCollaborator cc) throws DomainException {
		Collaborator collaborator = cc.findCollaborator(id);
		if(collaborator instanceof Teacher) {
			return true;
		}
		throw new DomainException("Colaborador não é um professor.");
	}
	
	public boolean isStudent(Collaborator collaborator) {
		if(collaborator instanceof Student) {
			return true;
		}
		return false;
	}
	
	public boolean checkId(int id) throws DomainException {
		for(Collaborator c : collaborators) {
			if(c.getId() == id) {
				return true;
			}
		}
		throw new DomainException("Id do colaborador inválido.");
	}
	
	public boolean checkType(int type) throws DomainException {
		if(type >= 1 && type <= 5) {
			return true;
		}
		throw new DomainException("Opção inválida.");
	}
	
	public boolean haveId(int id){
		for(Collaborator c : collaborators) {
			if(c.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public void printTeacher() {
		if(collaborators.size() > 0) {
			System.out.println("--------------Professores-----------");
			for(Collaborator c : collaborators) {
				if(c instanceof Teacher) {
					System.out.println("Id: " + c.getId() + "\nNome: " + c.getName());
					System.out.println("------------------------------------");	
				}
			}
		}
	}
	
	public void print() {
		if(collaborators.size() > 0) {
			System.out.println("------Colaboradores Cadastrados-----");
			for(Collaborator c : collaborators) {
				System.out.println("Id: " + c.getId() + "\nNome: " + c.getName());
				System.out.println("------------------------------------");	
			}
		}
	}
}
