package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import model.entities.Collaborator;
import model.entities.Orientation;
import model.entities.Publication;
import model.entities.Teacher;
import model.exceptions.DomainException;
import views.Menu;
import views.Utility;

public class ControllerAcademicProduction {
	
	private static Scanner sc = new Scanner(System.in);
	private static Random rd = new Random();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	
	protected ArrayList<Publication> publications = new ArrayList<Publication>();
	protected ArrayList<Orientation> orientations = new ArrayList<Orientation>();
	
	public void register(ControllerCollaborator controllerCollaborator){
		try {
			Menu.showMenuAcademicProduction();
			int type = Integer.parseInt(sc.nextLine());
			checkType(type);
			switch(type) {
				case 1: 
					register_publication(controllerCollaborator);
					break;
				case 2:
					register_orientation(controllerCollaborator);
					break;
			}
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
	
	public void register_publication(ControllerCollaborator controllerCollaborator) throws ParseException {
		System.out.print("Título: ");
		String title = sc.nextLine();
		int id = rd.nextInt(1000);
		while(haveIdPublication(id)) {
			id = rd.nextInt();
		}
		System.out.print("Conferência onde foi publicada: ");
		String conference = sc.nextLine();
		System.out.print("Ano de publicação: ");
		Date year = sdf.parse(sc.nextLine());
		Publication publication = new Publication(id, title, conference, year);
		publications.add(publication);
		System.out.println("\nPublicação foi cadastrada com sucesso!");
	}
	
	public void register_orientation(ControllerCollaborator controllerCollaborator) throws DomainException {
		System.out.print("Título: ");
		String title = sc.nextLine();
		int id = rd.nextInt(1000);
		while(haveIdOrientation(id)) {
			id = rd.nextInt();
		}
		controllerCollaborator.print();
		System.out.print("Id do professor: ");
		int idP = Integer.parseInt(sc.nextLine());
		controllerCollaborator.checkId(idP);
		Teacher teacher = (Teacher)controllerCollaborator.findTeacher(idP);
		Orientation orientation = new Orientation(id, title, teacher);
		orientations.add(orientation);
		teacher.addOrientation(orientation);
		System.out.println("\nOrientação foi cadastrada com sucesso!");
	}
	
	public void allocationOfAuthors(ControllerCollaborator controllerCollaborator) {		
		try {
			Menu.showMenuAcademicProduction();
			int type = Integer.parseInt(sc.nextLine());
			checkType(type);
			switch(type) {
			case 1:
				authorsInPublication(controllerCollaborator);
				break;
			case 2:
				authorsInOrientation(controllerCollaborator);
				break;
			}
		}
		catch(DomainException e) {
			System.out.println("\nErro: " + e.getMessage());
		}
		catch(NumberFormatException e) {
			System.out.println("\nErro: Opção inválida.");
		}
		finally {
			Utility.enter();
		}
	}
	
	public void authorsInPublication(ControllerCollaborator controllerCollaborator) throws DomainException {
		printPublications();
		controllerCollaborator.print();
		System.out.print("Id da publicação: ");
		int id = Integer.parseInt(sc.nextLine());
		checkIdPublication(id);
		System.out.print("Id do colaborador a ser alocado: ");
		int idC = Integer.parseInt(sc.nextLine());
		controllerCollaborator.checkId(idC);
		Collaborator collaborator = controllerCollaborator.findCollaborator(idC);
		Publication publication = findPublication(id);
		if(haveAuthorPublication(publication, idC)) {
			System.out.println("\nAutor já está alocado.");
		}else {
			publication.addAuthor(collaborator);
			collaborator.addPublication(publication);
			System.out.println("\nAutor foi alocado na publicação.");
		}
	}
	
	public void authorsInOrientation(ControllerCollaborator controllerCollaborator) throws DomainException {
		printOrientations();
		controllerCollaborator.print();
		System.out.print("Id da orientação: ");
		int id = Integer.parseInt(sc.nextLine());
		checkIdOrientation(id);
		System.out.print("Id do colaborador a ser alocado: ");
		int idC = Integer.parseInt(sc.nextLine());
		controllerCollaborator.checkId(idC);
		Collaborator collaborator = controllerCollaborator.findCollaborator(idC);
		Orientation orientation = findOrientation(id);
		if(haveAuthorOrientation(orientation, idC) || haveTeacher(orientation, idC)) {
			System.out.println("\nAutor já está alocado.");
		}else {
			orientation.addAuthor(collaborator);
			System.out.println("\nAutor foi alocado na orientação.");
		}
	}
	
	public boolean haveAuthorPublication(Publication publication, int id) {
		for(Collaborator c : publication.getAuthors()) {
			if(c.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean haveAuthorOrientation(Orientation orientation, int id) {
		for(Collaborator c : orientation.getAuthors()) {
			if(c.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean haveTeacher(Orientation orientation, int id) {
		if(orientation.getTeacher().getId() == id) {
			return true;
		}
		return false;
	}
	
	public Publication findPublication(int id) {
		for(Publication p : publications) {
	       if(p.getId() == id) {
	           return p;
	       }
		}
		return null;
	}
	
	public Orientation findOrientation(int id) {
		for(Orientation o : orientations) {
	       if(o.getId() == id) {
	           return o;
	       }
		}
		return null;
	}
	
	public boolean checkIdPublication(int id) throws DomainException {
		for(Publication p : publications) {
			if(p.getId() == id) {
				return true;
			}
		}
		throw new DomainException("Id da publicação inválido.");
	}
	
	public boolean checkIdOrientation(int id) throws DomainException {
		for(Orientation o : orientations) {
			if(o.getId() == id) {
				return true;
			}
		}
		throw new DomainException("Id da orientação inválido.");
	}
	
	public boolean haveIdPublication(int id){
		for(Publication p : publications) {
			if(p.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean haveIdOrientation(int id){
		for(Orientation o : orientations) {
			if(o.getId() == id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkType(int type) throws DomainException {
		if(type == 1 || type == 2) {
			return true;
		}
		throw new DomainException("Opção inválida.");
	}
	
	public void printPublications() {
		if(publications.size() > 0) {
			System.out.println("-------Publicações Cadastradas------");
			for(Publication p : publications) {
				System.out.println("Id: " + p.getId() + "\nTítulo: " + p.getTitle());
				System.out.println("------------------------------------");	
			}
		}
	}
	
	public void printOrientations() {
		if(orientations.size() > 0) {
			System.out.println("-------Orientações Cadastradas------");
			for(Orientation o : orientations) {
				System.out.println("Id: " + o.getId() + "\nTítulo: " + o.getTitle());
				System.out.println("------------------------------------");	
			}
		}
	}
	
}
