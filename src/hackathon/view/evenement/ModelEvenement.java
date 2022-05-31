package hackathon.view.evenement;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.commun.IMapper;
import hackathon.dao.DaoEvenement;
import hackathon.dao.DaoGroupe;
import hackathon.data.Evenement;
import hackathon.data.Groupe;
import hackathon.data.Jury;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;

public class ModelEvenement {
	
	@Inject
	private DaoEvenement daoEvenement;
	
	private final Evenement courant = new Evenement();
	
	// Gilles
	private final ObservableList<Evenement> liste = FXCollections.observableArrayList(); 
	

	private Groupe		selection;

  
	@Inject
	private IMapper		mapper;
    
	public void insertion() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		
		if( getCourant().getNom() == null || getCourant().getNom().isEmpty() ) {
			message.append( "\nLe nom ne doit pas être vide." );
		} else  if ( getCourant().getNom().length()> 30 ) {
			message.append( "\nLe nom est trop long." );
		}

		if( getCourant().getCode() == null || getCourant().getCode().isEmpty() ) {
			message.append( "\nLe prénom ne doit pas être vide." );
		} else if ( getCourant().getCode().length()> 30 ) {
			message.append( "\nLe prénom est trop long." );
		}
		
		if( getCourant().getTheme() == null || getCourant().getTheme().isEmpty() ) {
			message.append( "\nLe numéro de téléphone ne doit pas être vide." );
		} else if ( getCourant().getTheme().length()> 10 ) {
			message.append( "\nLe numéro de téléphone est trop long." );
		}
		
		if( getCourant().getLieu() == null || getCourant().getLieu().isEmpty() ) {
			message.append( "\nLe mail ne doit pas être vide." );
		} else if ( getCourant().getLieu().length()> 50 ) {
			message.append( "\nLe mail est trop long." );
		}else if ( getCourant().getLieu().length()> 10 ) {
			message.append( "\nLe numéro de téléphone est trop long." );
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
	

	public Groupe getSelection() {
		return selection;
	}
	
	public void setSelection( Groupe selection ) {
		this.selection = selection;
	}
	
	public void actualiserListe() {
		liste.setAll( daoEvenement.listerTout() );
 	}
	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Groupe();
		} else {
			//this.selection = daoEvenement.retrouver( selection.getId_groupe() );
		}
		// mapper.update( courant, selection );
	}
	
}
