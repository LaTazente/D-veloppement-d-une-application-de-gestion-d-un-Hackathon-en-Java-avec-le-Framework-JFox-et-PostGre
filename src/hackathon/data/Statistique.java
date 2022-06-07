package hackathon.data;

import java.util.List;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Statistique {

	private final StringProperty   	 	activite     	  	= new SimpleStringProperty();
	private final Property<Double>		taux_presence	= new SimpleObjectProperty<>();
	private final Property<Double>		taux_absence	= new SimpleObjectProperty<>();
	private final ObservableList<Participant>	participants	= FXCollections.observableArrayList(
		);
	
	private final ObservableList<Groupe>	groupes	= FXCollections.observableArrayList();
	
	private final ObservableList<Partenaire>	partenaires	= FXCollections.observableArrayList(
			
		);
	
	public final StringProperty activiteProperty() {
		return this.activite;
	}
	
	public final String getActivite() {
		return this.activiteProperty().get();
	}
	
	public final void setActivite(final String activite) {
		this.activiteProperty().set(activite);
	}
	
	public final Property<Double> taux_presenceProperty() {
		return this.taux_presence;
	}
	
	public final Double getTaux_presence() {
		return this.taux_presenceProperty().getValue();
	}
	
	public final void setTaux_presence(final Double taux_presence) {
		this.taux_presenceProperty().setValue(taux_presence);
	}
	
	public final Property<Double> taux_absenceProperty() {
		return this.taux_absence;
	}
	
	public final Double getTaux_absence() {
		return this.taux_absenceProperty().getValue();
	}
	
	public final void setTaux_absence(final Double taux_absence) {
		this.taux_absenceProperty().setValue(taux_absence);
	}
	
	public ObservableList<Groupe> getGroupes() {
		return groupes;
	}
	
	public ObservableList<Partenaire> getPartenaires() {
		return partenaires;
	}
	
	public ObservableList<Participant> getParticipants() {
		return participants;
	}
	
	
	
}
