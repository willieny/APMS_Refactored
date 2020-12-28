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
			System.out.print("Título: ");
			String title = sc.nextLine();
			int id = rd.nextInt(1000);
			switch(type) {
				case 1: 
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
					break;
				case 2:
					while(haveIdPublication(id)) {
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
	
	public void allocationOfAuthors(ControllerCollaborator controllerCollaborator) {		
		try {
			Menu.showMenuAcademicProduction();
			int type = Integer.parseInt(sc.nextLine());
			checkType(type);
			switch(type) {
				case 1:
					printPublications();
					break;
				case 2:
					printOrientations();
					break;
			}
			controllerCollaborator.print();
			System.out.print("Id da produção acadêmica: ");
			int id = Integer.parseInt(sc.nextLine());
			switch(type) {
			case 1:
				checkIdPublication(id);
				break;
			case 2:
				checkIdOrientation(id);
				break;
			}
			System.out.print("Id do colaborador a ser alocado: ");
			int idC = Integer.parseInt(sc.nextLine());
			controllerCollaborator.checkId(idC);
			Collaborator collaborator = controllerCollaborator.findCollaborator(idC);
			switch(type) {
			case 1:
				Publication publication = findPublication(id);
				if(haveAuthorPublication(publication, idC)) {
					System.out.println("\nAutor já está alocado.");
				}else {
					publication.addAuthor(collaborator);
					collaborator.addPublication(publication);
					System.out.println("\nAutor foi alocado na publicação.");
				}
				break;
			case 2:
				Orientation orientation = findOrientation(id);
				if(haveAuthorOrientation(orientation, idC) || haveTeacher(orientation, idC)) {
					System.out.println("\nAutor já está alocado.");
				}else {
					orientation.addAuthor(collaborator);
					System.out.println("\nAutor foi alocado na orientação.");
				}
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
