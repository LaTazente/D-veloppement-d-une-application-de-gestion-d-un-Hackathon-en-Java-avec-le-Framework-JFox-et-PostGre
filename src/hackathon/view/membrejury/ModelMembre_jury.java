package hackathon.view.membrejury;

import java.util.List;

import javax.inject.Inject;

import hackathon.commun.IMapper;
import hackathon.dao.DaoGroupe;
import hackathon.dao.DaoJury;
import hackathon.dao.DaoMembre_Jury;
import hackathon.dao.DaoService;
import hackathon.data.Groupe;
import hackathon.data.Jury;
import hackathon.data.Membre_jury;
import hackathon.data.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;

public class ModelMembre_jury {
	// Données observables 
	
			private final ObservableList<Membre_jury> liste = FXCollections.observableArrayList(); 
			private final ObservableList<Groupe> listeGroupe = FXCollections.observableArrayList();
			
			private final Membre_jury	courant = new Membre_jury();

			private static boolean isDisabled=false;
			// Autres champs
			
			private Membre_jury		selection;

			@Inject
			private IMapper			mapper;
		    @Inject
			private DaoMembre_Jury	daoMembre_jury;
		    @Inject 
		    private DaoGroupe		daoGroupe;
			// Getters & Setters
			
			public ObservableList<Membre_jury> getListe() {
				return liste;
			}
			
			public static boolean isDisabled() {
				return isDisabled;
			}

			public static void setDisabled(boolean isDisabled) {
				ModelMembre_jury.isDisabled = isDisabled;
			}

			public ObservableList<Groupe> getListeGroupe() {
				return listeGroupe;
			}
			
			public Membre_jury getCourant() {
				return courant;
			}
			
			public Membre_jury getSelection() {
				return selection;
			}
			
			public void setSelection( Membre_jury selection ) {
				this.selection = selection;
			}
			
			
			//actions
			public void actualiserListe() {
				liste.setAll( daoMembre_jury.listerTout() );
		 	}
			
//			public void listerGroupe() {
//				listeGroupe.setAll(Groupe.listerTout());
//				for(Groupe g:listeGroupe)System.out.println(g);
//			}
			
			public void actualiserCourant() {
				if ( selection == null ) {
					selection = new Membre_jury();
//					System.out.println(selection);
//					System.out.println("on est en cours d'actualisation dans le if");
				} else {
//					System.out.println(selection);
//					System.out.println("on est en cours d'actualisation dans le else");
					selection = daoMembre_jury.retrouver(selection.getCode_membre());
				}
				mapper.update( courant, selection );
				
			}
			
			public void validerMiseAJour() {

				// Vérifie la validité des données
				
				StringBuilder message = new StringBuilder();
				
				if( courant.getNom() == null ||  courant.getNom().isEmpty() ) {
					message.append( "\nLe nom ne doit pas être vide." );
				} else  if ( ((String) courant.getNom()).length()> 30 ) {
					message.append( "\nLe nom est trop long." );
				}

				if( courant.getPrenom() == null || courant.getPrenom().isEmpty() ) {
					message.append( "\nLe prénom ne doit pas être vide." );
				} else if ( ((String) courant.getPrenom()).length()> 30 ) {
					message.append( "\nLe prénom est trop long." );
				}
				
				if( courant.getTelephone() == null ||  courant.getTelephone().isEmpty() ) {
					message.append( "\nLe numéro de téléphone ne doit pas être vide." );
				} else if ( ((String) courant.getTelephone()).length()> 10 ) {
					message.append( "\nLe numéro de téléphone est trop long." );
				}
				
				if( courant.getEmail() == null ||  courant.getEmail().isEmpty() ) {
					message.append( "\nLe mail ne doit pas être vide." );
				} else if ( ((String) courant.getEmail()).length()> 50 ) {
					message.append( "\nLe mail est trop long." );
				}else if(!verifierMail(courant.getEmail())) {
					message.append( "\nLe mail doit être de la forme test@gmail.com" );
				}
				
				if ( message.length() > 0 ) {
					throw new ExceptionValidation( message.toString().substring(1) );
				}
				System.out.println(courant.getId_jury());
				// Effectue la mise à jour
//				if ( courant.getId_jury() == null) {
//					// Insertion
//					System.out.println("ID n'est pas null?");
//					System.out.println(courant.getId_jury());
//			} else {
//					// modficiation
//		//
//				System.out.println("Yes on va insérer!");
//					selection.setCode_membre( daoMembre_jury.inserer( courant ) );
//				}
//				
//				if(courant.getCode_membre() == "") {
//					selection.setCode_membre( daoMembre_jury.inserer( courant ) );
//				}else {
//					System.out.println("Impossible!");
//					System.out.println("["+courant.getId_jury()+"]");
//					daoMembre_jury.modifier(courant);
//					selection.setCode_membre( daoMembre_jury.inserer( courant ) );
//				}
//				
				
				// Effectue la mise à jour
				
				/*if ( courant.getId_jury() == null ) {
					// Insertion
					selection.setId_jury( daoMembre_jury.inserer( courant ) );
				} else {
					// modficiation
					daoMembre_jury.modifier( courant );
				}*/
				
				if(courant.getCode_membre()!=null && isDisabled==false) {
					selection.setCode_membre( daoMembre_jury.inserer( courant ) );
				}
				
				if(isDisabled==true) {
					daoMembre_jury.modifier(courant);
				}
			}
			
			public void supprimer( Membre_jury p ) {
				/*if ( DaoService.compterPourMembre_jury( p.getId_jury() ) != 0 ) {
					throw new ExceptionValidation( "Des services sont rattachés à cette personne." ) ;
				}*/
				daoMembre_jury.supprimer(p );
				selection = UtilFX.findNext( liste, p );
			}
			
			
			
			private boolean verifierMail(String mail) {
				if(mail.contains("@") && mail.split("@").length == 2) {
					if(mail.split("@")[0] != null && mail.split("@")[1] != null) {
						if(mail.split("@")[1].contains(".") && mail.split("@")[1].charAt(mail.split("@")[1].length()-1) != '.' && mail.split("@")[1].charAt(mail.split("@")[1].length()-2) != '.') {
							return true;
						}	
					}
				}
				return false;
			}

			public void modifier(Membre_jury p ) {
				daoMembre_jury.modifier(p);
				selection = UtilFX.findNext( liste, p );
			}
			
}
