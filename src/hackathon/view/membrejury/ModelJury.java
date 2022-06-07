package hackathon.view.membrejury;

import java.util.List;

import javax.inject.Inject;

import hackathon.commun.IMapper;
import hackathon.dao.DaoGroupe;
import hackathon.dao.DaoJury;
import hackathon.dao.DaoPersonne;
import hackathon.dao.DaoService;
import hackathon.data.Groupe;
import hackathon.data.Jury;
import hackathon.data.Personne;
import hackathon.data.Telephone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;

public class ModelJury {

	// Données observables 
	
		private final ObservableList<Jury> liste = FXCollections.observableArrayList(); 
		//private final ObservableList<Groupe> listeGroupe = FXCollections.observableArrayList();
		
		private final Jury	courant = new Jury();

		
		// Autres champs
		
		private Jury		selection;

		@Inject
		private IMapper			mapper;
	    @Inject
		private DaoJury	daoJury;
	    @Inject 
	    private DaoGroupe		daoGroupe;
		// Getters & Setters
		
		public ObservableList<Jury> getListe() {
			return liste;
		}
		
		/*public ObservableList<Groupe> getListeGroupe() {
			return listeGroupe;
		}*/
		
		public Jury getCourant() {
			return courant;
		}
		
		public Jury getSelection() {
			return selection;
		}
		
		public void setSelection( Jury selection ) {
			this.selection = selection;
		}
		
		
		//actions
		public void actualiserListe() {
			liste.setAll( daoJury.listeJury() );
	 	}
		
		/*public void listerGroupe() {
			listeGroupe.setAll(DaoGroupe.listerTout());
			for(DaoGroupe g:listeGroupe)System.out.println(g);
		}*/
		
		public void actualiserCourant() {
			if ( selection == null ) {
				selection = new Jury();
//				System.out.println(selection);
//				System.out.println("on est en cours d'actualisation dans le if");
			} else {
//				System.out.println(selection);
//				System.out.println("on est en cours d'actualisation dans le else");
				selection = daoJury.retrouver( selection.getId_jury() );
			}
			mapper.update( courant, selection );
			
		}
		
		public void validerMiseAJour() {

			// Vérifie la validité des données
			
			StringBuilder message = new StringBuilder();
			
//			if( courant.getNom() == null || ((List<Jury>) courant.getNom()).isEmpty() ) {
//				message.append( "\nLe nom ne doit pas être vide." );
//			} else  if ( ((String) courant.getNom()).length()> 30 ) {
//				message.append( "\nLe nom est trop long." );
//			}
//
//			if( courant.getPrenom() == null || ((List<Jury>) courant.getPrenom()).isEmpty() ) {
//				message.append( "\nLe prénom ne doit pas être vide." );
//			} else if ( ((String) courant.getPrenom()).length()> 30 ) {
//				message.append( "\nLe prénom est trop long." );
//			}
//			
//			if( courant.getTelephone() == null || ((List<Jury>) courant.getTelephone()).isEmpty() ) {
//				message.append( "\nLe numéro de téléphone ne doit pas être vide." );
//			} else if ( ((String) courant.getTelephone()).length()> 10 ) {
//				message.append( "\nLe numéro de téléphone est trop long." );
//			}
//			
//			if( courant.getEmail() == null || courant.getEmail().isEmpty() ) {
//				message.append( "\nLe mail ne doit pas être vide." );
//			} else if ( ((String) courant.getEmail()).length()> 50 ) {
//				message.append( "\nLe mail est trop long." );
//			}else if(!verifierMail(courant.getEmail())) {
//				message.append( "\nLe mail doit être de la forme test@gmail.com" );
//			}
//			
//			if ( message.length() > 0 ) {
//				throw new ExceptionValidation( message.toString().substring(1) );
//			}
//			System.out.println(courant.getId());
			// Effectue la mise à jour
//			if ( courant.getId() == "" ) {
//				// Insertion
//				System.out.println("ID n'est pas null?");
//				System.out.println(courant.getId());
//			} else {
//				// modficiation
//				//daoParticicpant.modifier( courant );
	//
//				System.out.println("Yes on va insérer!");
//				selection.setId( daoParticicpant.inserer( courant ) );
//			}
			
			if(courant.getId_jury() == "*") {
				selection.setId_jury( daoJury.inserer( courant ) );
			}else {
//				System.out.println("Impossible!");
//				System.out.println("["+courant.getId()+"]");
				daoJury.modifier(courant);
			}
		}
		
		public void supprimer( Jury p ) {
			
			daoJury.supprimer( Integer.parseInt(p.getId_jury()) );
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
		

}
