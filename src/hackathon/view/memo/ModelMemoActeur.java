package hackathon.view.memo;

import java.time.LocalDate;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import hackathon.commun.IMapper;
import hackathon.dao.DaoPersonne;
import hackathon.data.Agir;
import hackathon.data.Personne;

public class ModelMemoActeur {
	
	
	// Données observables 
	
	private final Agir	courant = new Agir();
	
	private final ObservableList<Personne> acteursPossibles = FXCollections.observableArrayList(); 


	
	// Autres champs
	
	private Agir		selection;

	@Inject
	private IMapper		mapper;
    @Inject
	private ModelMemo	modelMemo;
    @Inject
    private DaoPersonne	daoPersonne;
	
	
	// Getters & Setters
	
	public Agir getCourant() {
		return courant;
	}
	
	public Agir getSelection() {
		return selection;
	}
	
	public void setSelection( Agir selection ) {
		this.selection = selection;
	}
	
	public ObservableList<Personne> getActeursPossibles() {
		return acteursPossibles;
	}
	
	
	// Actions
	
	public void actualiserActeursPossibles() {
		acteursPossibles.setAll( daoPersonne.listerTout() );
		for ( Agir item : modelMemo.getCourant().getActeurs() ) {
			if ( selection == null || ! item.getPersonne().equals(selection.getPersonne()) ) {
				acteursPossibles.remove( item.getPersonne() );
			}
		}
 	}

	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Agir();
			selection.setMemo( modelMemo.getCourant() );
			selection.setDebut( LocalDate.now() );
		}
		mapper.update( courant, selection );
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getPersonne() == null ) {
			message.append( "\nUne personne doit être indiquée." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( selection.getPersonne() == null ) {
			// Insertion
			modelMemo.getCourant().getActeurs().add( selection );
		}
		mapper.update( selection, courant );
	}
	
}
