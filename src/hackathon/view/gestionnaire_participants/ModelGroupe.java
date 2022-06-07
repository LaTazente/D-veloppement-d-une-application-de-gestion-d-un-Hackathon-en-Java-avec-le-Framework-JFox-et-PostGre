package hackathon.view.gestionnaire_participants;


import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import hackathon.commun.IMapper;
import hackathon.dao.DaoEffectuer;
import hackathon.dao.DaoGroupe;
import hackathon.dao.DaoParticipant;
import hackathon.data.Activite;
import hackathon.data.Groupe;
import hackathon.data.Service;
import hackathon.data.Statistique;


public class ModelGroupe {
	
	
	// Données observables 
	
	private final ObservableList<Groupe> liste = FXCollections.observableArrayList(); 
	
	private final Groupe	courant = new Groupe();
	
	private  static boolean isDisabled = false;
	
	private final Statistique statistique = new Statistique();
	
	// Autres champs
	
	// is Disabled
	 public static boolean isDisabled() {
			return isDisabled;
		}

	public static void setDisabled(boolean isDisabled) {
		ModelGroupe.isDisabled = isDisabled;
	}


	private Groupe		selection;

   


	@Inject
	private IMapper		mapper;
    @Inject
	private DaoGroupe	daoGroupe;
    @Inject
    private DaoEffectuer daoEffectuer;
    @Inject
	 private DaoParticipant daoParticipant;

	
	// Getters & Setters
	
	public ObservableList<Groupe> getListe() {
		return liste;
	}

	public Groupe getCourant() {
		return courant;
	}
	
	public Groupe getSelection() {
		return selection;
	}
	
	public void setSelection( Groupe selection ) {
		this.selection = selection;
	}
	
//	public void setSelection( Groupe selection ) {
//		if ( selection == null ) {
//			this.selection = new Groupe();
//		} else {
//			// this.selection = daoGroupe.retrouver( selection.getId_groupe() );
//		}
//	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoGroupe.listerTout() );
		//System.out.println("on est la"+ daoGroupe.retrouverChefParGroupe("G1").getNom());
 	}

	
//	public void actualiserCourant() {
//		mapper.update( courant, selection );
//	}
	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Groupe();
		} else {
			this.selection = daoGroupe.retrouver( selection.getId_groupe() );
		}
		mapper.update( courant, selection );
	}
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		
//		if( courant.getPseudo() == null || courant.getPseudo().isEmpty() ) {
//			message.append( "\nLe pseudo ne doit pas être vide." );
//		} else 	if ( courant.getPseudo().length() < 3 ) {
//			message.append( "\nLe pseudo est trop court : 3 mini." );
//		} else  if ( courant.getPseudo().length()> 25 ) {
//			message.append( "\nLe pseudo est trop long : 25 maxi." );
//		} else 	if ( ! daoGroupe.verifierUnicitePseudo( courant.getPseudo(), courant.getId() ) ) {
//			message.append( "\nLe pseudo " + courant.getPseudo() + " est déjà utilisé." );
//		}
		System.out.println("nom" + courant.getId_groupe());
		if( courant.getId_groupe() == null || courant.getId_groupe().isEmpty() ) {
			message.append( "\nL'id de l'equipe ne doit pas être vide." );
		} else  if ( courant.getId_groupe().length()< 3 ) {
			message.append( "\nL'id de l'equipe est trop court : 3 mini." );
		} else  if ( courant.getId_groupe().length()> 7 ) {
			message.append( "\nL'id de l'equipe est trop long : 7 maxi." );
		}
	
		System.out.println("nom" + courant.getNom());
		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
			message.append( "\nLe nom de l'equipe ne doit pas être vide." );
		} else  if ( courant.getNom().length()< 3 ) {
			message.append( "\nLe nom de l'equipe est trop court : 3 mini." );
		} else  if ( courant.getNom().length()> 25 ) {
			message.append( "\nLe nom de l'equipe est trop long : 25 maxi." );
		}
//		
//		if( courant.getEmail() == null || courant.getEmail().isEmpty() ) {
//			message.append( "\nL'adresse e-mail ne doit pas être vide." );
//		} else  if ( courant.getEmail().length()> 100 ) {
//			message.append( "\nL'adresse e-mail est trop longue : 100 maxi." );
//		}
//		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		// selection.setId_groupe( daoGroupe.inserer( courant ) );
		// System.out.println("ici "+ courant.getId_groupe());
		// System.out.println("ici "+ courant.getId_groupe());
		// System.out.println("ici2 "+ selection.getId_groupe());
		System.out.println("ici "+ courant.getId_groupe());
		if ( courant.getId_groupe() != null &&  isDisabled == false) {
			// Insertion*
			System.out.println("inserer ");
			selection.setId_groupe( daoGroupe.inserer( courant ) );
			
		}
		
		if ( isDisabled == true ){
			// modficiation
			System.out.println("modifier ");
			daoGroupe.modifier( courant );
		}
	}
	
	public String getNomChef(Groupe g) {
		return daoGroupe.retrouverChefParGroupe(g.getId_groupe());
	}
	
	public int getNombreMembres(Groupe g) {
		return daoGroupe.getNombreMembres(g);
	}
	
	public void supprimer( Groupe item ) {
		daoGroupe.supprimer( item.getId_groupe() );
		selection = UtilFX.findNext( liste, item );
	}
	
	public void insererChoix(Groupe g,List<Activite> l) {
		daoEffectuer.insererPourGroupe(g, l);
	}

	public void getMembres(String idGroupe) {
		statistique.getParticipants().setAll(daoParticipant.retrouverMembresParGroupe(idGroupe));
	}
	
	// Getters
	public Statistique getStatistique() {
		return statistique;
	}
	
	
	public void supprimerChoix(Groupe g,List<Activite> l) {
		daoEffectuer.supprimerPourGroupe(g, l);
	}
	
	public List<Activite> getDefisRestants(Groupe g){
		return daoEffectuer.getDefisGroupe(g);
	}
	public List<Activite> getDefisChoisis(Groupe g){
		return daoEffectuer.getDefisChoisisGroupe(g);
	}

}
