package hackathon.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Memo {
	
	
	// Champs
	
	private final Property<Integer>		id 			= new SimpleObjectProperty<>();
	private final StringProperty		titre		= new SimpleStringProperty();
	private final StringProperty		description	= new SimpleStringProperty();
	private final Property<Boolean>		flagUrgent	= new SimpleObjectProperty<>();
	private final Property<Categorie>	categorie	= new SimpleObjectProperty<>();
	private final StringProperty		statut		= new SimpleStringProperty();
	private final Property<Integer>		effectif	= new SimpleObjectProperty<>();
	private final Property<BigDecimal>	budget		= new SimpleObjectProperty<>();
	private final Property<LocalDate> 	echeance	= new SimpleObjectProperty<>();
	private final Property<LocalTime> 	heure		= new SimpleObjectProperty<>();
	private final ObservableList<Compte> abonnes 	= FXCollections.observableArrayList();	
	private final ObservableList<Agir> 	acteurs 	= FXCollections.observableArrayList();	
	
	// Getters & Setters
	
	public final Property<Integer> idProperty() {
		return this.id;
	}
	
	public final Integer getId() {
		return this.idProperty().getValue();
	}
	
	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
	}
	
	public final StringProperty titreProperty() {
		return this.titre;
	}
	
	public final String getTitre() {
		return this.titreProperty().get();
	}
	
	public final void setTitre(final String titre) {
		this.titreProperty().set(titre);
	}
	
	public final StringProperty descriptionProperty() {
		return this.description;
	}
	
	public final String getDescription() {
		return this.descriptionProperty().get();
	}
	
	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}
	
	public final Property<Boolean> flagUrgentProperty() {
		return this.flagUrgent;
	}
	
	public final Boolean getFlagUrgent() {
		return this.flagUrgentProperty().getValue();
	}
	
	public final void setFlagUrgent(final Boolean flagUrgent) {
		this.flagUrgentProperty().setValue(flagUrgent);
	}

	public final Property<Categorie> categorieProperty() {
		return this.categorie;
	}

	public final Categorie getCategorie() {
		return this.categorieProperty().getValue();
	}

	public final void setCategorie(final Categorie categorie) {
		this.categorieProperty().setValue(categorie);
	}

	public final StringProperty statutProperty() {
		return this.statut;
	}

	public final String getStatut() {
		return this.statutProperty().getValue();
	}

	public final void setStatut(final String statut) {
		this.statutProperty().setValue(statut);
	}

	public final Property<Integer> effectifProperty() {
		return this.effectif;
	}

	public final Integer getEffectif() {
		return this.effectifProperty().getValue();
	}

	public final void setEffectif(final Integer effectif) {
		this.effectifProperty().setValue(effectif);
	}

	public final Property<BigDecimal> budgetProperty() {
		return this.budget;
	}

	public final BigDecimal getBudget() {
		return this.budgetProperty().getValue();
	}

	public final void setBudget(final BigDecimal budget) {
		this.budgetProperty().setValue(budget);
	}

	public final Property<LocalDate> echeanceProperty() {
		return this.echeance;
	}

	public final LocalDate getEcheance() {
		return this.echeanceProperty().getValue();
	}

	public final void setEcheance(final LocalDate echeance) {
		this.echeanceProperty().setValue(echeance);
	}

	public final Property<LocalTime> heureProperty() {
		return this.heure;
	}

	public final LocalTime getHeure() {
		return this.heureProperty().getValue();
	}

	public final void setHeure(final LocalTime heure) {
		this.heureProperty().setValue(heure);
	}
	
	public ObservableList<Compte> getAbonnes() {
		return abonnes;
	}
	
	public ObservableList<Agir> getActeurs() {
		return acteurs;
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
		Memo other = (Memo) obj;
		return Objects.equals(id.getValue(), other.id.getValue());
	}

}

