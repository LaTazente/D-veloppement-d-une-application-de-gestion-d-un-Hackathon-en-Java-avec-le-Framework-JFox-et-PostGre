package hackathon.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Activite {

	private final Property<Integer> id_activite = new SimpleObjectProperty<>();
	private final StringProperty nom = new SimpleStringProperty();
	private final StringProperty type = new SimpleStringProperty();
	private final StringProperty duree = new SimpleStringProperty();
	private final Property<Evenement> hackathon = new SimpleObjectProperty<>();
	
	public Activite() {
		
	}
	
	public Activite(Integer id_activite, String nom, String type, String duree) {
		this.setId_activite(id_activite);
		this.setNom(nom);
		this.setType(type);
		this.setDuree(duree);
	}
	
	public final Property<Integer> id_activiteProperty(){
		return id_activite;
	}
	public final Integer getId_activite() {
		return id_activite.getValue();
	}
	public final void setId_activite(Integer id_activite) {
		this.id_activite.setValue(id_activite);
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
	public final  StringProperty typeProperty() {
		return this.type;
	}
	public final String getType() {
		return this.type.getValue();
	}
	public final void setType(String type) {
		this.type.setValue(type);
	}
	public final  StringProperty dureeProperty() {
		return this.duree;
	}
	public final String getDuree() {
		return this.duree.getValue();
	}
	public final void setDuree(String duree) {
		this.type.setValue(duree);
	}
	public final Property<Evenement> hackathonProperty(){
		return hackathon;
	}
	public final Evenement getHackathon() {
		return this.hackathon.getValue();
	}
	public final void setHackathon(Evenement hackathon) {
		this.hackathon.setValue(hackathon);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id_activite, nom);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activite other = (Activite) obj;
		return Objects.equals(id_activite, other.id_activite) && Objects.equals(nom, other.nom);
	}
	@Override
	public String toString() {
		return "Activite [id_activite=" + id_activite + ", nom=" + nom + ", type=" + type + ", duree=" + duree + "]";
	}
	
	
}
