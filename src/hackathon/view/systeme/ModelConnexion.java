package hackathon.view.systeme;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import jfox.exception.ExceptionValidation;
import jfox.javafx.view.IManagerGui;
import hackathon.dao.DaoIntervenir;
import hackathon.dao.DaoUtilisateur;
import hackathon.data.Utilisateur;
import hackathon.view.EnumView;
import hackathon.view.ManagerGui;


public class ModelConnexion {
	
	// Logger
	public static final Logger logger = Logger.getLogger( ModelConnexion.class.getName() );
	
	
	// Données observables 
	
	// Vue connexion
	private final Utilisateur			courant = new Utilisateur();

	// Utilisateur connecté
	private final Property<Utilisateur>	compteActif = new SimpleObjectProperty<>();

	
	// Autres champs
	@Inject
	private DaoUtilisateur	daoUtilisateur;
	
	@Inject
	private DaoIntervenir	daoIntervenir;

	// Getters 
	
	public Utilisateur getCourant() {
		return courant;
	}
	
	public Property<Utilisateur> compteActifProperty() {
		return compteActif;
	}
	
	public Utilisateur getCompteActif() {
		return compteActif.getValue();
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		courant.setId_user( "geek" );
		courant.setMdp( "geek" );
	}
	
	
	// Actions


	public void ouvrirSessionUtilisateur() {

		Utilisateur utilisateur = daoUtilisateur.validerAuthentification(
					courant.id_userProperty().getValue(), courant.mdpProperty().getValue() );
		
		if( courant.getCode() == null ) {
			throw new ExceptionValidation( "Veuillez selectionner un hackathon" );
		} 
		
		if( utilisateur == null ) {
			throw new ExceptionValidation( "Pseudo ou mot de passe invalide." );
		} else {
			Utilisateur utilisateur2 = daoIntervenir.verifierUtilisateurParHackathon(utilisateur, courant.getCode().getCode());
			if( utilisateur2 == null ) {
				System.out.println("pas existant");
				throw new ExceptionValidation( "Pas d'utilisateur pour ce hackathon" );
				
			} else {
				//Platform.runLater( () -> compteActif.setValue( utilisateur ) );
				System.out.println("existant");
				// System.out.println(daoIntervenir.listerPourUtilisateur(utilisateur2).get(0));
				if(daoIntervenir.listerPourUtilisateur(utilisateur2).get(0).equals("Administrateur")) {
					System.out.println("admin");
					
				}
				
			}
		}
	}
	

	public void fermerSessionUtilisateur() {
		compteActif.setValue( null );
	}

	public void getRoleUtilisateur() {
		
	}
}
