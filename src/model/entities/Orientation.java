package model.entities;

public class Orientation extends AcademicProduction{

	private Teacher teacher;

	public Orientation(int id, String title, Teacher teacher) {
		super(id, title);
		this.teacher = teacher;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Override
	public String toString() {
		if(getAuthors().size() > 0) {
			return "\nId: " + getId() + "\nTítulo: " + getTitle() + "\nAutores: " + getAuthors();
		}
		else {
			return "\nId: " + getId() + "\nTítulo: " + getTitle() + "\nSem autores.";
		}
	}
	
}
