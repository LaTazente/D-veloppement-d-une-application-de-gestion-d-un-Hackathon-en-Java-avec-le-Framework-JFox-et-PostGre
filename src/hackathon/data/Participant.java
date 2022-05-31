package hackathon.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Participant {
	
	private final StringProperty id = new SimpleStringProperty();
	private final StringProperty nom = new SimpleStringProperty();
	private final StringProperty prenom = new SimpleStringProperty();
	private final StringProperty role = new SimpleStringProperty();
	private final StringProperty sexe = new SimpleStringProperty();
	private final StringProperty telephone = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final Property<Groupe> idGroupe = new SimpleObjectProperty<Groupe>();
	
	
	public Participant() { // construction d'un objet vide
		this.setId("*");	
	}
	public Participant(String id, String nom,String prenom,String role,String sexe,String telephone,String email ,Groupe groupe) {
		this.setId(id);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setRole(role);
		this.setSexe(sexe);
		this.setTelephone(telephone);
		this.setEmail(email);
		this.setIdGroupe(groupe);
	}
	
	
	
	public final  StringProperty idProperty() {
		return this.id;
	}
	public final String getId() {
		return this.id.getValue();
	}
	public final void setId(String id) {
		this.id.setValue(id);
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
	public final  StringProperty prenomProperty() {
		return this.prenom;
	}
	public final String getPrenom() {
		return this.prenom.getValue();
	}
	public final void setPrenom(String prenom) {
		this.prenom.setValue(prenom);
	}
	public final  StringProperty roleProperty() {
		return this.role;
	}
	public final String getRole() {
		return this.role.getValue();
	}
	public final void setRole(String role) {
		this.role.setValue(role);
	}
	public final  StringProperty sexeProperty() {
		return this.sexe;
	}
	public final String getSexe() {
		return this.sexe.getValue();
	}
	public final void setSexe(String sexe) {
			this.sexe.setValue(sexe);
	}
	public final  StringProperty telephoneProperty() {
		return this.telephone;
	}
	public final String getTelephone() {
		return this.telephone.getValue();
	}
	public final void setTelephone(String telephone) {
		this.telephone.setValue(telephone);
	}
	public final  StringProperty emailProperty() {
		return this.email;
	}
	public final String getEmail() {
		return this.email.getValue();
	}
	public final void setEmail(String email) {
		this.email.setValue(email);
	}
	public final  Property<Groupe> idGroupeProperty() {
		return this.idGroupe;
	}
	public final Groupe getIdGroupe() {
		return this.idGroupe.getValue();
	}
	public final void setIdGroupe(Groupe groupe) {
		this.idGroupe.setValue(groupe);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id, nom, prenom, sexe, telephone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		return Objects.equals(id, other.id) && Objects.equals(nom, other.nom) && Objects.equals(prenom, other.prenom)
				&& Objects.equals(sexe, other.sexe) && Objects.equals(telephone, other.telephone);
	}
	
	@Override
	public String toString() {
		return "Participant [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", role=" + role + ", sexe=" + sexe
				+ ", telephone=" + telephone + ", email=" + email +", idGroupe=" + idGroupe + "]";
	}

	
	

}
