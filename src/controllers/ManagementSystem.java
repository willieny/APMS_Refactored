package controllers;

import java.util.Scanner;

import views.Menu;
import views.Utility;

public class ManagementSystem {
	
	private static Scanner sc = new Scanner(System.in);
	
	public void MainSystem(){
		ControllerProject controllerProject = null;
		controllerProject = ControllerProject.getInstance();
		
		ControllerCollaborator controllerCollaborator = null;
		controllerCollaborator = ControllerCollaborator.getInstance();
		
		ControllerAcademicProduction controllerAcademic = null;
		controllerAcademic = ControllerAcademicProduction.getInstance();
		
		boolean sair = false;
		int option;
		while(!sair){
			try {
				Utility.clearScreen();
				Menu.showMenu();
				option = Integer.parseInt(sc.nextLine());
				switch(option) {
					case 1:
						controllerCollaborator.register();
						break;
					case 2:
						controllerProject.register();
						break;
					case 3:
					    controllerAcademic.register(controllerCollaborator);
						break;	
					case 4:
						controllerProject.editProjectInformation();
						break;
					case 5:
						controllerProject.statusChange();
						break;
					case 6:
						controllerProject.allocationOfParticipants(controllerCollaborator);
						break;
					case 7:
						controllerAcademic.allocationOfAuthors(controllerCollaborator);
						break;
					case 8:
						controllerProject.associatePublication(controllerAcademic);;
						break;
					case 9: 
						controllerProject.removeCollaborator(controllerCollaborator);
						break;
					case 10: 
						controllerCollaborator.consultCollaborator();
						break;
					case 11:
						controllerProject.consultProject();
						break;
					case 12:
						System.out.println("#----------- Relatório de produções do laboratório -----------#");
						System.out.println("| Número de colaboradores: " + controllerCollaborator.collaborators.size() 
						+ "                                  |");
						System.out.println("| Número de projetos em elaboração: " + controllerProject.numberOfInPreparation()
						+ "                         |");
						System.out.println("| Número de projetos em andamento: " + controllerProject.numberOfInProcess()
						+ "                          |");
						System.out.println("| Número de projetos concluídos: " + controllerProject.numberOfConcluded() 
						+ "                            |");
						System.out.println("| Número total de projetos: " + controllerProject.projects.size()
						+ "                                 |");
						System.out.println("| Número de produções acadêmicas:                             |" + "\n| - publicações: " 
						+ controllerAcademic.publications.size() 
						+ "                                            |" + "\n| - orientações: " + controllerAcademic.orientations.size() + "                                            |");
						System.out.println("#-------------------------------------------------------------#");
						Utility.enter();
						break;
					case 0:
						sair = true;
						break;
					default:
					    System.out.println("\nErro: Opção inválida.");
					    Utility.enter();
				}
			}
			catch(NumberFormatException e) {
				System.out.println("\nErro: Opção inválida.");
				Utility.enter();
			}
		}
		
	}	
}
