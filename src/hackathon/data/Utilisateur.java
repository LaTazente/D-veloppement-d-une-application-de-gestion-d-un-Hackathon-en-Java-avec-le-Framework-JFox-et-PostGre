package hackathon.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utilisateur {
	
	private final StringProperty id_user = new SimpleStringProperty();
	private final StringProperty nom = new SimpleStringProperty();
	private final StringProperty prenom = new SimpleStringProperty();
	private final StringProperty profession = new SimpleStringProperty();
	private final StringProperty telephone = new SimpleStringProperty();
	private final StringProperty mdp = new SimpleStringProperty();
	private final StringProperty confirmMdp = new SimpleStringProperty();
	private final Property<Evenement>	    code				= new SimpleObjectProperty<>();
	private final StringProperty	role 		= new SimpleStringProperty();
	
	
	
	public final StringProperty id_userProperty() {
		return this.id_user;
	}
	
	public final String getId_user() {
		return this.id_userProperty().getValue();
	}
	
	public final void setId_user(final String id_user) {
		this.id_userProperty().set(id_user);
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
	
	public final StringProperty prenomProperty() {
		return this.prenom;
	}
	
	public final String getPrenom() {
		return this.prenomProperty().getValue();
	}
	
	public final void setPrenom(final String prenom) {
		this.prenomProperty().set(prenom);
	}
	
	public final StringProperty professionProperty() {
		return this.profession;
	}
	
	public final String getProfession() {
		return this.professionProperty().getValue();
	}
	
	public final void setProfession(final String profession) {
		this.professionProperty().set(profession);
	}
	
	public final StringProperty telephoneProperty() {
		return this.telephone;
	}
	
	public final String getTelephone() {
		return this.telephoneProperty().getValue();
	}
	
	public final void setTelephone(final String telephone) {
		this.telephoneProperty().set(telephone);
	}
	
	public final StringProperty mdpProperty() {
		return this.mdp;
	}
	
	public final String getMdp() {
		return this.mdpProperty().getValue();
	}
	
	public final void setMdp(final String mdp) {
		this.mdpProperty().set(mdp);
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

	public final StringProperty roleProperty() {
		return this.role;
	}
	

	public final String getRole() {
		return this.roleProperty().get();
	}
	

	public final void setRole(final String role) {
		this.roleProperty().set(role);
	}

	public final StringProperty confirmMdpProperty() {
		return this.confirmMdp;
	}
	

	public final String getConfirmMdp() {
		return this.confirmMdpProperty().getValue();
	}
	

	public final void setConfirmMdp(final String confirmMdp) {
		this.confirmMdpProperty().set(confirmMdp);
	}
	
	

}
