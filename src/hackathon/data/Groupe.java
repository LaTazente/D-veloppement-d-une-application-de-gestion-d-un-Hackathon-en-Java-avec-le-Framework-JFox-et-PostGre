package hackathon.data;

import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Groupe {
	
	
	// Champs
	
	private final StringProperty   	 	id_groupe     	  	= new SimpleStringProperty();
	private final StringProperty   	 	nom     	  	= new SimpleStringProperty();
	private final Property<Integer>		nbre_menbres	= new SimpleObjectProperty<>();
	private final Property<Jury>	    id_jury				= new SimpleObjectProperty<>();
	
	// Getters & setters


	public StringProperty id_groupeProperty() {
		return this.id_groupe;
	}
	


	public final String getId_groupe() {
		return this.id_groupeProperty().getValue();
	}
	


	public final void setId_groupe(final String id_groupe) {
		this.id_groupeProperty().set(id_groupe);
	}
	

	public final StringProperty nomProperty() {
		return this.nom;
	}
	

	public final String getNom() {
		return this.nomProperty().getValue();
	}
	

	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}
	

	public final Property<Integer> nbre_menbresProperty() {
		return this.nbre_menbres;
	}
	

	public final Integer getNbre_menbres() {
		return this.nbre_menbresProperty().getValue();
	}
	

	public final void setNbre_menbres(final Integer nbre_menbres) {
		this.nbre_menbresProperty().setValue(nbre_menbres);
	}

	public final Property<Jury> id_juryProperty() {
		return this.id_jury;
	}
	


	public final Jury getId_jury() {
		return this.id_juryProperty().getValue();
	}
	


	public final void setId_jury(final Jury id_jury) {
		this.id_juryProperty().setValue(id_jury);
	}
	
	
	// hashCode() & equals()
	
	



	@Override
	public int hashCode() {
		return Objects.hash(id_groupe, id_jury, nbre_menbres, nom);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Groupe other = (Groupe) obj;
		return Objects.equals(id_groupe, other.id_groupe) && Objects.equals(id_jury, other.id_jury)
				&& Objects.equals(nbre_menbres, other.nbre_menbres) && Objects.equals(nom, other.nom);
	}


	
	
}
