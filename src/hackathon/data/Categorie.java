package hackathon.data;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Categorie  {
	

	// Données observables
	
	private final Property<Integer>	id		= new SimpleObjectProperty<>();
	private final StringProperty	libelle	= new SimpleStringProperty();
	private final Property<LocalDate> debut	= new SimpleObjectProperty<>();
	
	
	// Constructeurs
	
	public Categorie() {
	}

	public Categorie( final int id, final String libelle ) {
		setId(id);
		setLibelle(libelle);
	}
	
	
	// Getters et Setters

	public final Property<Integer> idProperty() {
		return this.id;
	}

	public final Integer getId() {
		return this.idProperty().getValue();
	}

	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
	}

	public final StringProperty libelleProperty() {
		return this.libelle;
	}

	public final String getLibelle() {
		return this.libelleProperty().getValue();
	}

	public final void setLibelle(final String libelle) {
		this.libelleProperty().setValue(libelle);
	}

	public final Property<LocalDate> debutProperty() {
		return this.debut;
	}

	public final LocalDate getDebut() {
		return this.debutProperty().getValue();
	}

	public final void setDebut(final LocalDate debut) {
		this.debutProperty().setValue(debut);
	}
	
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(id.getValue() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		return Objects.equals(id.getValue(), other.id.getValue() );
	}

	
	// toString()
	
	@Override
	public String toString() {
		return libelle.getValue();
	}
	
}

