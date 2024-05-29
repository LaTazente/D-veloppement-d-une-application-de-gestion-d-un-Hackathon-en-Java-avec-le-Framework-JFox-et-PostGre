package hackathon.view.jury;

import java.time.LocalDate;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import hackathon.commun.IMapper;
import hackathon.dao.DaoJury;
import hackathon.data.Jury;


public class ModelJury  {
	
	
	// Données observables 
	
	private final ObservableList<Jury> liste = FXCollections.observableArrayList(); 
	
	private final Jury	courant = new Jury();

	
	// Autres champs
	
	private Jury		selection;

	@Inject
	private IMapper		mapper;
    @Inject
	private DaoJury	daoJury;
	
	
	// Getters & Setters
	
	public ObservableList<Jury> getListe() {
		return liste;
	}
	
	public Jury getCourant() {
		return courant;
	}
	
	public Jury getSelection() {
		return selection;
	}
	
	public void setSelection( Jury selection ) {
		this.selection = selection;
	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoJury.listerTout() );
 	}

	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Jury();
			// selection.setFlagSiege(false);
			// selection.setAnneeCreation( LocalDate.now().getYear() );
		} else {
			selection = daoJury.retrouver( selection.getId_jury() );
		}
		// mapper.update( courant, selection );
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

//		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
//			message.append( "\nLe titre ne doit pas être vide." );
//		} else  if ( courant.getNom().length()< 3 ) {
//			message.append( "\nLe nom est trop court : 3 caractères. au moins." );
//		} else  if ( courant.getNom().length()> 50 ) {
//			message.append( "\nLe nom est trop long : 50 maxi." );
//		}
//		
//		if ( message.length() > 0 ) {
//			throw new ExceptionValidation( message.toString().substring(1) );
//		}
//		
//		
//		// Effectue la mise à jour
//		
//		if ( courant.getId() == null ) {
//			// Insertion
//			selection.setId( daoJury.inserer( courant ) );
//		} else {
//			// modficiation
//			daoJury.modifier( courant );
//		}
	}
	
	
	public void supprimer( Jury item ) {
		// daoJury.supprimer( item.getId_jury() );
		// selection = UtilFX.findNext( liste, item );
	}
	
}
