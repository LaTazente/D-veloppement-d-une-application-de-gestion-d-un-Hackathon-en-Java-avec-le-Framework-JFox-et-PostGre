package hackathon.view.personne;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import hackathon.commun.IMapper;
import hackathon.dao.DaoPersonne;
import hackathon.dao.DaoService;
import hackathon.data.Personne;
import hackathon.data.Telephone;


public class ModelPersonne {
	
	
	// Données observables 
	
	private final ObservableList<Personne> liste = FXCollections.observableArrayList();
	
	private final Personne	courant = new Personne();
	
	
	// Autres champs
	
	private Personne		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoPersonne		daoPersonne;
    @Inject
    private DaoService		daoService;
	
	
	// Getters & Setters
	
	public ObservableList<Personne> getListe() {
		return liste;
	}
	
	public Personne getCourant() {
		return courant;
	}
	
	public Personne getSelection() {
		return selection;
	}
	
	public void setSelection( Personne selection ) {
		this.selection = selection;
	}

	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoPersonne.listerTout() );
	}

	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Personne();
		} else {
			selection = daoPersonne.retrouver( selection.getId() );
		}
		mapper.update( courant, selection );
	}

	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		
		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
			message.append( "\nLe nom ne doit pas être vide." );
		} else  if ( courant.getNom().length()> 25 ) {
			message.append( "\nLe nom est trop long." );
		}

		if( courant.getPrenom() == null || courant.getPrenom().isEmpty() ) {
			message.append( "\nLe prénom ne doit pas être vide." );
		} else if ( courant.getPrenom().length()> 25 ) {
			message.append( "\nLe prénom est trop long." );
		}

		if( courant.getCategorie() == null ) {
			message.append( "\nLe catégorie doit être indiquée." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}

		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoPersonne.inserer( courant ) );
		} else {
			// modficiation
			daoPersonne.modifier( courant );
		}
	}
	

	public void supprimer( Personne item ) {
		
		// Vérifie l'abence de services rattachés à la personne
		if ( daoService.compterPourPersonne( item.getId() ) != 0 ) {
			throw new ExceptionValidation( "Des services sont rattachés à cette personne." ) ;
		}
		
		daoPersonne.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}
	

	public void ajouterTelephone() {
		courant.getTelephones().add( new Telephone() );
	}
	

	public void supprimerTelephone( Telephone telephone )  {
		courant.getTelephones().remove( telephone );
	}
	
}