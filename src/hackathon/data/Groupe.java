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
	private final Property<Evenement>	    code				= new SimpleObjectProperty<>();
	
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


	public final Property<Evenement> codeProperty() {
		return this.code;
	}
	



	public final Evenement getCode() {
		return this.codeProperty().getValue();
	}
	



	public final void setCode(final Evenement code) {
		this.codeProperty().setValue(code);
	}
	
	// hashCode() & equals()



	


	
	
}
