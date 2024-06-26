package hackathon.view.categorie;

import java.time.LocalDate;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import hackathon.commun.IMapper;
import hackathon.dao.DaoCategorie;
import hackathon.dao.DaoMemo;
import hackathon.dao.DaoPersonne;
import hackathon.data.Categorie;


public class ModelCategorie  {
	
	
	// Données observables 
	
	private final ObservableList<Categorie> liste = FXCollections.observableArrayList(); 
	
	private final Categorie	courant = new Categorie();

	
	// Autres champs
	
	private Categorie		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoCategorie	daoCategorie;
    @Inject
    private DaoPersonne		daoPersonne;
    @Inject
    private DaoMemo			daoMemo;
	
	
	// Getters & Setters
	
	public ObservableList<Categorie> getListe() {
		return liste;
	}
	
	public Categorie getCourant() {
		return courant;
	}
	
	public Categorie getSelection() {
		return selection;
	}
	
	public void setSelection( Categorie selection ) {
		this.selection = selection;
	}
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoCategorie.listerTout() );
 	}

	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Categorie();
			selection.setDebut( LocalDate.now() );
		} else {
			selection = daoCategorie.retrouver( selection.getId() );
		}
		mapper.update( courant, selection );
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getLibelle() == null || courant.getLibelle().isEmpty() ) {
			message.append( "\nLe libellé ne doit pas être vide." );
		} else  if ( courant.getLibelle().length()> 25 ) {
			message.append( "\nLe libellé est trop long : 25 maxi." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoCategorie.inserer( courant ) );
		} else {
			// modficiation
			daoCategorie.modifier( courant );
		}
	}
	
	
	public void supprimer( Categorie item ) {
		
		// Vérifie l'abence de personnes rattachées à la catégorie
		if ( daoPersonne.compterPourCategorie( item.getId() ) != 0 ) {
			throw new ExceptionValidation( "Des personnes sont rattachées à cette catégorie." ) ;
		}
		
		// Vérifie l'abence de mémos rattachés à la catégorie
		if ( daoMemo.compterPourCategorie( item.getId() ) != 0 ) {
			throw new ExceptionValidation( "Des mémos sont rattachés à cette catégorie." ) ;
		}
		
		daoCategorie.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
	}
	
}
