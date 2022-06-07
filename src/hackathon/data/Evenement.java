package hackathon.data;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Evenement {
	
	private final StringProperty code = new SimpleStringProperty();
	private final StringProperty nom = new SimpleStringProperty();
	private final StringProperty theme = new SimpleStringProperty();
	private final StringProperty lieu = new SimpleStringProperty();
	private final Property<LocalDate> dateDebut = new SimpleObjectProperty<>();
	private final Property<LocalDate> dateFin = new SimpleObjectProperty<>();
	
	public Evenement() {
		
	}
	
	public Evenement(String code, String nom, String theme, String lieu, LocalDate dateDebut, LocalDate dateFin){
		this.setCode(code);
		this.setNom(nom);
		this.setTheme(theme);
		this.setLieu(lieu);
		this.setDateDebut(dateDebut);
		this.setDateFin(dateFin);
	}
	
	
	public final  StringProperty codeProperty() {
		return this.code;
	}
	public final String getCode() {
		return this.code.getValue();
	}
	public final void setCode(String code) {
		this.code.setValue(code);
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
	public final  StringProperty themeProperty() {
		return this.theme;
	}
	public final String getTheme() {
		return this.theme.getValue();
	}
	public final void setTheme(String theme) {
		this.theme.setValue(theme);
	}
	public final  StringProperty lieuProperty() {
		return this.lieu;
	}
	public final String getLieu() {
		return this.lieu.getValue();
	}
	public final void setLieu(String lieu) {
		this.lieu.setValue(lieu);
	}
	public final  Property<LocalDate> dateDebutProperty() {
		return this.dateDebut;
	}
	public final LocalDate getDateDebut() {
		return this.dateDebut.getValue();
	}
	public final void setDateDebut(LocalDate dateDebut) {
		this.dateDebut.setValue(dateDebut);
	}
	public final  Property<LocalDate> dateFinProperty() {
		return this.dateFin;
	}
	public final LocalDate getDateFin() {
		return this.dateFin.getValue();
	}
	public final void setDateFin(LocalDate dateFin) {
		this.dateFin.setValue(dateFin);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, dateDebut, dateFin, nom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evenement other = (Evenement) obj;
		return Objects.equals(code, other.code) && Objects.equals(dateDebut, other.dateDebut)
				&& Objects.equals(dateFin, other.dateFin) && Objects.equals(nom, other.nom);
	}

	@Override
	public String toString() {
		return getCode()+"";
	}
	
	
	
}
