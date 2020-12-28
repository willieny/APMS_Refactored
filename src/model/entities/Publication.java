package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Publication extends AcademicProduction implements Comparable<Publication>{

	private String conference;
	private Date yearPublication;
	private Project project;
	
	private static  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	
	public Publication(int id, String title, String conference, Date yearPublication) {
		super(id, title);
		this.conference = conference;
		this.yearPublication = yearPublication;
	}

	public String getConference() {
		return conference;
	}

	public void setConference(String conference) {
		this.conference = conference;
	}

	public Date getYearPublication() {
		return yearPublication;
	}

	public void setYearPublication(Date yearPublication) {
		this.yearPublication = yearPublication;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "\nId: " + getId() + "\nTítulo: " + getTitle() + "\nConferência: " + conference + "\nAno de publicação: " + sdf.format(yearPublication);
	}

	@Override
	public int compareTo(Publication o) {
		if (getYearPublication() == null || o.getYearPublication() == null)
		      return 0;
		return getYearPublication().compareTo(o.getYearPublication());
	}
	
}
