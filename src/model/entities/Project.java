package model.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.enums.StatusProject;
import model.exceptions.DomainException;

public class Project implements Comparable<Project>{

	private int id;
	private String title;
	private Date start;
	private Date finish;
	private String fundingAgency;
	private Double amount;
	private String objective;
	private String description;
	
	private StatusProject status;
	
	private ArrayList<Collaborator> collaborators = new ArrayList<Collaborator>();
	private ArrayList<Publication> publications = new ArrayList<Publication>();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Project(int id, String title, Date start, Date finish, String fundingAgency, Double amount, String objective,
			String description, StatusProject status) throws DomainException {
		if(!finish.after(start)) {
			throw new DomainException("A data de término deve ser depois da data de início.");
		}
		this.id = id;
		this.title = title;
		this.start = start;
		this.finish = finish;
		this.fundingAgency = fundingAgency;
		this.amount = amount;
		this.objective = objective;
		this.description = description;
		this.status = StatusProject.IN_PREPARATION;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}

	public String getFundingAgency() {
		return fundingAgency;
	}

	public void setFundingAgency(String fundingAgency) {
		this.fundingAgency = fundingAgency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StatusProject getStatus() {
		return status;
	}

	public void setStatus(StatusProject status) {
		this.status = status;
	}
	
	public ArrayList<Collaborator> getCollaborators() {
		return collaborators;
	}

	public void addCollaborator(Collaborator collaborator) {
		collaborators.add(collaborator);
	}
	
	public void removeCollaborator(Collaborator collaborator) {
		collaborators.remove(collaborator);
	}

	public ArrayList<Publication> getPublications() {
		return publications;
	}

	public void addPublication(Publication publication) {
		publications.add(publication);
	}
	
	public void removePublication(Publication publication) {
		publications.remove(publication);
	}
	
	@Override
	public String toString() {
		return "\nid: " + getId()+ "\nTítulo: " + getTitle() + "\nData de início: " + sdf.format(start) + "\nData de término: " + sdf.format(finish)
				+ "\nAgência financiadora: " + getFundingAgency() + "\nValor financiado: R$ " + String.format("%.2f", amount) + "\nObjetivo: "
				+ getObjective() + "\nDescrição: " + getDescription() + "\nStatus: " + getStatus().getStatusProject();
	}

	@Override
	public int compareTo(Project o) {
		if (getFinish() == null || o.getFinish() == null)
		      return 0;
		return getFinish().compareTo(o.getFinish());
	}
	
}
