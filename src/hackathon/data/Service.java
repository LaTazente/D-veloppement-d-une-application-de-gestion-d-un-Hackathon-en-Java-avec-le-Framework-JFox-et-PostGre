package hackathon.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Service {
	
	
	// Champs
	
	private final Property<Integer>		id				= new SimpleObjectProperty<>();
	private final StringProperty   	 	nom     	  	= new SimpleStringProperty();
	private final Property<Integer>		anneeCreation	= new SimpleObjectProperty<>();
	private final Property<Boolean>		flagSiege		= new SimpleObjectProperty<>();
	private final Property<Personne>	personne		= new SimpleObjectProperty<>();

	
	// Getters & setters

	public final Property<Integer> idProperty() {
		return this.id;
	}
	
	public final Integer getId() {
		return this.idProperty().getValue();
	}
	
	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
	}
	
	public final StringProperty nomProperty() {
		return this.nom;
	}
	
	public final String getNom() {
		return this.nomProperty().get();
	}
	
	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}
	
	public final Property<Integer> anneeCreationProperty() {
		return this.anneeCreation;
	}
	
	public final Integer getAnneeCreation() {
		return this.anneeCreationProperty().getValue();
	}
	
	public final void setAnneeCreation(final Integer anneeCreation) {
		this.anneeCreationProperty().setValue(anneeCreation);
	}
	
	public final Property<Boolean> flagSiegeProperty() {
		return this.flagSiege;
	}
	
	public final Boolean getFlagSiege() {
		return this.flagSiegeProperty().getValue();
	}
	
	public final void setFlagSiege(final Boolean flagSiege) {
		this.flagSiegeProperty().setValue(flagSiege);
	}

	public final Property<Personne> categorieProperty() {
		return this.personne;
	}

	public final Personne getPersonne() {
		return this.categorieProperty().getValue();
	}

	public final void setPersonne(final Personne categorie) {
		this.categorieProperty().setValue(categorie);
	}
	
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(id.getValue());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Service other = (Service) obj;
		return Objects.equals(id.getValue(), other.id.getValue());
	}
	
}
