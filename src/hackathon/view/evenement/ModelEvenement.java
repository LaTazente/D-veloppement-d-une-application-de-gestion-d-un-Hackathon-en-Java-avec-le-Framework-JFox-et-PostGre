package hackathon.view.evenement;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.commun.IMapper;
import hackathon.dao.DaoActivite;
import hackathon.dao.DaoEvenement;
import hackathon.dao.DaoGroupe;
import hackathon.dao.DaoInscrire;
import hackathon.dao.DaoPartenaire;
import hackathon.dao.DaoParticipant;
import hackathon.dao.DaoEvenement;
import hackathon.data.Evenement;
import hackathon.data.Groupe;
import hackathon.data.Evenement;
import hackathon.data.Jury;
import hackathon.data.Statistique;
import hackathon.view.ManagerGui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;

public class ModelEvenement {
	
	@Inject
	private DaoEvenement daoEvenement;
	
	private final Evenement courant = new Evenement();
	
	private final Statistique statistique = new Statistique();
	
	// Gilles
	private final ObservableList<Evenement> liste = FXCollections.observableArrayList(); 
	

	private Evenement		selection;

  
	@Inject
	private IMapper		mapper;
	
	//dao pour les statistiques
	 @Inject
	 private DaoActivite daoActivite;
	 @Inject
	 private DaoInscrire daoInscrire;
	 @Inject
	 private DaoGroupe daoGroupe;
	 @Inject
	 private DaoParticipant daoParticipant;
	 @Inject
	 private DaoPartenaire daoPartenaire;
    
	public void insertion() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		
		if( getCourant().getNom() == null || getCourant().getNom().isEmpty() ) {
			message.append( "\nLe nom ne doit pas être vide." );
		} else  if ( getCourant().getNom().length()> 30 ) {
			message.append( "\nLe nom est trop long." );
		}

		if( getCourant().getCode() == null || getCourant().getCode().isEmpty() ) {
			message.append( "\nLe code ne doit pas être vide." );
		} else if ( getCourant().getCode().length()> 30 ) {
			message.append( "\nLe code est trop long." );
		}
		
		if( getCourant().getTheme() == null || getCourant().getTheme().isEmpty() ) {
			message.append( "\nLe theme ne doit pas être vide." );
		} else if ( getCourant().getTheme().length()> 50 ) {
			message.append( "\nLe theme est trop long." );
		}
		
		if( getCourant().getLieu() == null || getCourant().getLieu().isEmpty() ) {
			message.append( "\nLe lieu ne doit pas être vide." );
		} else if ( getCourant().getLieu().length()> 20 ) {
			message.append( "\nLe lieu est trop long." );
		}
		
		if ( message.length() > 0 ) { 
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		if(courant == null) {
			System.out.println(" yess!");
		}else {
			daoEvenement.inserer(courant);
		}
	}

	public Evenement getCourant() {
		return courant;
	}
	
	// Gilles
	public ObservableList<Evenement> getListe() {
		return liste;
	}
	

	public Evenement getSelection() {
		return selection;
	}
	
	public void setSelection( Evenement selection ) {
		this.selection = selection;
	}
	
	public void actualiserListe() {
		liste.setAll( daoEvenement.listerTout() );
 	}
	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Evenement();
		} else {
			this.selection = daoEvenement.retrouver( selection.getCode() );
		}
		mapper.update( courant, selection );
	}
	
	
	public void supprimer( Evenement item ) {
		daoEvenement.supprimer( item.getCode() );
		selection = UtilFX.findNext( liste, item );
	}
	
	public void actualiserStatistiques(String idEvenement) {
		statistique.setActivite(daoActivite.dureeActivites(idEvenement));
		statistique.setTaux_presence(daoInscrire.taux_participation_presence(idEvenement));
		statistique.setTaux_absence(daoInscrire.taux_participation_absence(idEvenement));
		//daoGroupe.nbre_groupe("G1");
		//statistique.setParticipant(daoParticipant.recherche_participants(idEvenement));
		//daoPartenaire.recherche_partenaires(idEvenement);
		//statistique.getParticipants().setAll(daoParticipant.recherche_participants(idEvenement));
		statistique.getPartenaires().setAll(daoPartenaire.recherche_partenaires(idEvenement));
		statistique.getGroupes().setAll(daoGroupe.recherche_groupes(idEvenement));
		
	}
	
	// getters et setters stats
	public Statistique getStatistique() {
		return statistique;
	}
}
