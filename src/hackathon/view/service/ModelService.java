package hackathon.view.service;

import java.time.LocalDate;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import hackathon.commun.IMapper;
import hackathon.dao.DaoService;
import hackathon.data.Service;


public class ModelService  {
	
	
	// Données observables 
	
	private final ObservableList<Service> liste = FXCollections.observableArrayList(); 
	
	private final Service	courant = new Service();

	
	// Autres champs
	
	private Service		selection;

	@Inject
	private IMapper		mapper;
    @Inject
	private DaoService	daoService;
	
	
	// Getters & Setters
	
	public ObservableList<Service> getListe() {
		return liste;
	}
	
	public Service getCourant() {
		return courant;
	}
	
	public Service getSelection() {
		return selection;
	}
	
	public void setSelection( Service selection ) {
		this.selection = selection;
	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoService.listerTout() );
 	}

	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Service();
			selection.setFlagSiege(false);
			selection.setAnneeCreation( LocalDate.now().getYear() );
		} else {
			selection = daoService.retrouver( selection.getId() );
		}
		mapper.update( courant, selection );
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
			message.append( "\nLe titre ne doit pas être vide." );
		} else  if ( courant.getNom().length()< 3 ) {
			message.append( "\nLe nom est trop court : 3 caractères. au moins." );
		} else  if ( courant.getNom().length()> 50 ) {
			message.append( "\nLe nom est trop long : 50 maxi." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoService.inserer( courant ) );
		} else {
			// modficiation
			daoService.modifier( courant );
		}
	}
	
	
	public void supprimer( Service item ) {
		daoService.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}
	
}
