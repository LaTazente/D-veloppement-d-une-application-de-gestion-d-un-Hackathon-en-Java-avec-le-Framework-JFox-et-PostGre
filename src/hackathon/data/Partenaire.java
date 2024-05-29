package hackathon.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partenaire {
	
	private final StringProperty nom = new SimpleStringProperty();
	private final StringProperty description = new SimpleStringProperty();
	private final Property<Evenement>	    code				= new SimpleObjectProperty<>();
	
	
	public Partenaire() { // construction d'un objet vide
		this.setNom("*");	
	}
	public Partenaire( String nom,String description) {
		
		this.setNom(nom);
		this.setDescription(description);
		
	}
	
	public final  StringProperty nomProperty() {
		return this.nom;
	}
	public final String getNom() {
		return this.nom.getValue();
	}
	public final void setNom(String nom) {
		this.nom.setValue(nom);
	}
	public final  StringProperty descriptionProperty() {
		return this.description;
	}
	public final String getDescription() {
		return this.description.getValue();
	}
	public final void setDescription(String description) {
		this.description.setValue(description);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash( nom, description );
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partenaire other = (Partenaire) obj;
		return  Objects.equals(nom, other.nom) && Objects.equals(description, other.description);
	}
	
	@Override
	public String toString() {
		return "Partenaire [ nom=" + nom + ", description=" + description +  "]";
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
	

	
	

}








/*package hackathon.data;

import java.util.Objects;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Partenaire  {
	

	// Données observables
	
	private final StringProperty	nom	= new SimpleStringProperty();
	private final StringProperty	description	= new SimpleStringProperty();
	private final ObservableList<Partenaire>	partenaires	= FXCollections.observableArrayList(
			p ->  new Observable[] { p.nomProperty(), p.descriptionProperty() } 
		);
	
	
	
	// Constructeurs
	
	public Partenaire() {
	}

	public Partenaire( final String nom, final String description ) {
		setNom(nom);
		setDescription(description);
	}
	
	
	// Getters et Setters

	public final StringProperty nomProperty() {
		return this.nom;
	}

	public final String getNom() {
		return this.nomProperty().getValue();
	}

	public final void setNom(final String nom) {
		this.nomProperty().setValue(nom);
	}
	 
	public final StringProperty descriptionProperty() {
		return this.description;
	}

	public final String getDescription() {
		return this.descriptionProperty().getValue();
	}

	public final void setDescription(final String description) {
		this.nomProperty().setValue(description);
	}

	public ObservableList<Partenaire> getPartenaires() {
		return partenaires;
	}
	
	
	// toString()
	
	@Override
	public int hashCode() {
		return Objects.hash(description, nom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partenaire other = (Partenaire) obj;
		return Objects.equals(description, other.description) && Objects.equals(nom, other.nom);
	}

	@Override
	public String toString() {
		return "nom=" + nom + ", description=" + description ;
	}

	

	
	
	
	
}*/

