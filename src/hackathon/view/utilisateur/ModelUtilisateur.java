package hackathon.view.utilisateur;


import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import hackathon.commun.IMapper;
import hackathon.dao.DaoUtilisateur;
import hackathon.data.Utilisateur;
import hackathon.data.Service;


public class ModelUtilisateur {
	
	
	// Données observables 
	
	private final ObservableList<Utilisateur> liste = FXCollections.observableArrayList(); 
	
	private final Utilisateur	courant = new Utilisateur();
	
	
	
	// Autres champs


	private Utilisateur		selection;

   


	@Inject
	private IMapper		mapper;
    @Inject
	private DaoUtilisateur	daoUtilisateur;
	
	
	// Getters & Setters
	
	public ObservableList<Utilisateur> getListe() {
		return liste;
	}

	public Utilisateur getCourant() {
		return courant;
	}
	
	public Utilisateur getSelection() {
		return selection;
	}
	
	public void setSelection( Utilisateur selection ) {
		this.selection = selection;
	}
	
//	public void setSelection( Utilisateur selection ) {
//		if ( selection == null ) {
//			this.selection = new Utilisateur();
//		} else {
//			// this.selection = daoUtilisateur.retrouver( selection.getId_groupe() );
//		}
//	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoUtilisateur.listerTout() );
		// System.out.println("on est la"+ daoUtilisateur.retrouverChefParUtilisateur("G1").getNom());
 	}

	
//	public void actualiserCourant() {
//		mapper.update( courant, selection );
//	}
	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Utilisateur();
		} else {
			this.selection = daoUtilisateur.retrouver( selection.getId_user() );
		}
		mapper.update( courant, selection );
	}
	
	public void validerMiseAJour(List tab) {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		
		if( courant.getId_user() == null || courant.getId_user().isEmpty() ) {
			message.append( "\nLe pseudo ne doit pas être vide." );
		} else 	if ( courant.getId_user().length() < 3 ) {
			message.append( "\nLe pseudo est trop court : 3 mini." );
		} else  if ( courant.getId_user().length()> 25 ) {
			message.append( "\nLe pseudo est trop long : 25 maxi." );
		} else 	if ( ! daoUtilisateur.verifierUnicitePseudo( courant.getId_user()) ) {
			message.append( "\nLe pseudo " + courant.getId_user() + " est déjà utilisé." );
		}
		
		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
			message.append( "\nLe nom de l'utilisateur ne doit pas être vide." );
		} else  if ( courant.getNom().length()< 3 ) {
			message.append( "\nLe nom de l'utilisateur est trop court : 3 mini." );
		} else  if ( courant.getNom().length()> 25 ) {
			message.append( "\nLe nom de l'utilisateur est trop long : 25 maxi." );
		}
		
		if( courant.getPrenom() == null || courant.getPrenom().isEmpty() ) {
			message.append( "\nLe prenom de l'utilisateur ne doit pas être vide." );
		} else  if ( courant.getPrenom().length()< 3 ) {
			message.append( "\nLe prenom de l'utilisateur est trop court : 3 mini." );
		} else  if ( courant.getPrenom().length()> 25 ) {
			message.append( "\nLe prenom de l'utilisateur est trop long : 25 maxi." );
		}
		
		if( courant.getTelephone() == null || courant.getTelephone().isEmpty() ) {
			message.append( "\nLe numéro de téléphone ne doit pas être vide." );
		} else if ( courant.getTelephone().length()> 10 ) {
			message.append( "\nLe numéro de téléphone est trop long." );
		}
		
		if( courant.getProfession() == null || courant.getProfession().isEmpty() ) {
			message.append( "\nLa profession ne doit pas être vide." );
		} else if ( courant.getTelephone().length()> 10 ) {
			message.append( "\nLa profession est trop long." );
		}
		
		if( courant.getMdp() == null || courant.getMdp().isEmpty() ) {
			message.append( "\nLe mot de passe ne doit pas être vide." );
		}
		
		if( courant.getConfirmMdp() == null || courant.getConfirmMdp().isEmpty() ) {
			message.append( "\nLe champ de confirmation de  mot de passe ne doit pas être vide." );
		}
		
		if( ! courant.getConfirmMdp().equals(courant.getMdp())) {
			message.append( "\nLes mots de passes ne sont pas identiques." );
			System.out.println("ici mdp "+ courant.getMdp());
			System.out.println("ici confirmmdp "+ courant.getConfirmMdp());
		}
		
		if( tab.size() == 0 ) {
			message.append( "\nVeuillez choisir un role pour l'utilisateur" );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		// selection.setId_groupe( daoUtilisateur.inserer( courant ) );
		// System.out.println("ici "+ courant.getId_groupe());
		// System.out.println("ici "+ courant.getId_groupe());
		// System.out.println("ici2 "+ selection.getId_groupe());
		System.out.println("ici "+ courant.getId_user());
		if ( courant.getId_user() != null) {
			// Insertion*
			System.out.println("inserer ");
			//selection.setId_user(null);
			selection.setId_user( daoUtilisateur.inserer( courant , tab) );
		}
		
	}
	
	
	public void supprimer( Utilisateur item ) {
		daoUtilisateur.supprimer( item.getId_user() );
		selection = UtilFX.findNext( liste, item );
	}

}
