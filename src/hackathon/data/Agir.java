package hackathon.data;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Agir {
	
	
	// Champs
	
	private final Property<Memo>		memo 		= new SimpleObjectProperty<>();
	private final Property<Personne>	personne 	= new SimpleObjectProperty<>();
	private final StringProperty		fonction	= new SimpleStringProperty();
	private final Property<LocalDate>	debut 		= new SimpleObjectProperty<>();
	
	
	// Getters & Setters
	
	public final Property<Memo> memoProperty() {
		return this.memo;
	}
	
	public final Memo getMemo() {
		return this.memoProperty().getValue();
	}
	
	public final void setMemo(final Memo memo) {
		this.memoProperty().setValue(memo);
	}
	
	public final Property<Personne> personneProperty() {
		return this.personne;
	}
	
	public final Personne getPersonne() {
		return this.personneProperty().getValue();
	}
	
	public final void setPersonne(final Personne personne) {
		this.personneProperty().setValue(personne);
	}
	
	public final StringProperty fonctionProperty() {
		return this.fonction;
	}
	
	public final String getFonction() {
		return this.fonctionProperty().get();
	}
	
	public final void setFonction(final String fonction) {
		this.fonctionProperty().set(fonction);
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
		return Objects.hash(memo, personne);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agir other = (Agir) obj;
		return Objects.equals(memo, other.memo) && Objects.equals(personne, other.personne);
	}

}
