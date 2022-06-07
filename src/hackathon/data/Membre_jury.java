package hackathon.data;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Membre_jury {
	// Champs
	
		private final Property<String>		code_membre				= new SimpleStringProperty();
		private final Property<String>		nom				= new SimpleStringProperty();
		private final Property<String>		prenom				= new SimpleStringProperty();
		private final Property<String>		email			= new SimpleStringProperty();
		private final Property<String>		profession				= new SimpleStringProperty();
		private final Property<String>		telephone		= new SimpleObjectProperty<>();
		private final Property<Jury>		id_jury				= new SimpleObjectProperty<>();
		private final Property<Groupe>		groupe				= new SimpleObjectProperty<>();
		
		
		// Getters & setters

		
		public final Property<String> nomProperty() {
			return this.nom;
		}
		
		public final String getNom() {
			return this.nomProperty().getValue();
		}
		
		public final void setNom(final String nom) {
			this.nomProperty().setValue(nom);
		}

		public final Property<String> code_membreProperty() {
			return this.code_membre;
		}
		

		public final String getCode_membre() {
			return this.code_membre.getValue();
		}
		

		public final void setCode_membre(final String code_membre) {
			this.code_membre.setValue(code_membre);
		}
		
		public final Property<String> prenomProperty() {
			return this.prenom;
		}
		
		public final String getPrenom() {
			return this.prenomProperty().getValue();
		}
		
		public final void setPrenom(final String prenom) {
			this.prenomProperty().setValue(prenom);
		}
		public final Property<String> emailProperty() {
			return this.email;
		}
		
		public final String getEmail() {
			return this.emailProperty().getValue();
		}
		
		public final void setEmail(final String email) {
			this.emailProperty().setValue(email);
		}
		public final Property<String> telephoneProperty() {
			return this.telephone;
		}
		
		public final String getTelephone() {
			return this.telephoneProperty().getValue();
		}
		
		public final void setTelephone(final String telephone) {
			this.telephoneProperty().setValue(telephone);
		}
		public final Property<String> professionProperty() {
			return this.profession;
		}
		
		public final String getProfession() {
			return this.professionProperty().getValue();
		}
		
		public final void setProfession(final String profession) {
			this.professionProperty().setValue(profession);
		}
		public final Property<Jury> id_juryProperty() {
			return this.id_jury;
		}
		
		public final Jury getId_jury() {
			return this.id_juryProperty().getValue();
		}
		
		public final void setId_jury(final Jury id_jury) {
			this.id_juryProperty().setValue(id_jury);
		}

		public Property<Groupe> groupeProperty() {
			return this.groupe;
		}

		
}
