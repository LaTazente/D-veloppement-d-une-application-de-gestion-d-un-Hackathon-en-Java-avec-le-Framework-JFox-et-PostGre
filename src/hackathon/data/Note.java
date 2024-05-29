package hackathon.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class Note {
	
	private final Property<Groupe> groupe = new SimpleObjectProperty<>();
	private final Property<Activite> defi = new SimpleObjectProperty<>();
	private final Property<String> note = new SimpleObjectProperty<>();
	
	public Note() {
		
	}
	
	public Note(Groupe g, Activite a,String n) {
		this.setGroupe(g);
		this.setNote(n);
		this.setDefi(a);
	}

	public final Property<Groupe> groupeProperty() {
		return this.groupe;
	}
	

	public final Groupe getGroupe() {
		return this.groupeProperty().getValue();
	}
	

	public final void setGroupe(final Groupe groupe) {
		this.groupeProperty().setValue(groupe);
	}
	

	public final Property<Activite> defiProperty() {
		return this.defi;
	}
	

	public final Activite getDefi() {
		return this.defiProperty().getValue();
	}
	

	public final void setDefi(final Activite defi) {
		this.defiProperty().setValue(defi);
	}
	

	public  Property<String> noteProperty() {
		return this.note;
	}
	

	public  String getNote() {
		return this.note.getValue();
	}
	

	public  void setNote( String note) {
		this.note.setValue(note);
	}

	@Override
	public int hashCode() {
		return Objects.hash(defi, groupe, note);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		return Objects.equals(defi, other.defi) && Objects.equals(groupe, other.groupe)
				&& Objects.equals(note, other.note);
	}

	@Override
	public String toString() {
		return "Note [groupe=" + groupe + ", defi=" + defi + ", note=" + note + "]";
	}
	
	
	
	
}
