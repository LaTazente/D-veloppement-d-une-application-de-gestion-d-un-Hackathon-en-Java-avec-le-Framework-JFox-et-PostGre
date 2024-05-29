package hackathon.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Jury {
	
	
	// Champs
	
	private final Property<String>		id_jury				= new SimpleStringProperty();;
	private final Property<Integer>		nbre_memenbres		= new SimpleObjectProperty<>();
	private final Property<Evenement>		code				= new SimpleObjectProperty<>();

	
	// Getters & setters

	
	public final Property<Integer> nbre_memenbresProperty() {
		return this.nbre_memenbres;
	}
	
	public final Integer getNbre_memenbres() {
		return this.nbre_memenbresProperty().getValue();
	}
	
	public final void setNbre_memenbres(final Integer nbre_memenbres) {
		this.nbre_memenbresProperty().setValue(nbre_memenbres);
	}

	public final Property<String> id_juryProperty() {
		return this.id_jury;
	}
	

	public final String getId_jury() {
		return this.id_juryProperty().getValue();
	}
	

	public final void setId_jury(final String id_jury) {
		this.id_juryProperty().setValue(id_jury);
	}

	public final Property<Evenement> codeProperty() {
		return this.code;
	}
	

	public final Evenement getCode() {
		return this.codeProperty().getValue();
	}
	

	public final void setCode(final Evenement code) {
		this.codeProperty().setValue(code);
	}

	@Override
	public String toString() {
		return getId_jury()+"";
	}
	
	
	
	
	
	// hashCode() & equals()

	
}
