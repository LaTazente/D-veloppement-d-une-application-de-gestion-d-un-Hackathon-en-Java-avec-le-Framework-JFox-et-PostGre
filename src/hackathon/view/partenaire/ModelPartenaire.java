package hackathon.view.partenaire;

import javax.inject.Inject;

import hackathon.commun.IMapper;
import hackathon.dao.DaoAssister;
import hackathon.dao.DaoPartenaire;
import hackathon.data.Partenaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.javafx.util.UtilFX;

public class ModelPartenaire {
	
	// Données observables 
	
	private final ObservableList<Partenaire> liste = FXCollections.observableArrayList(); 
	//private final ObservableList<Groupe> listeGroupe = FXCollections.observableArrayList();
	
	private final Partenaire	courant = new Partenaire();

	
	// Autres champs
	
	private Partenaire		selection;

	@Inject
	private IMapper			mapper;
    @Inject
	private DaoPartenaire	daoPartenaire;
    @Inject
	private DaoAssister	daoAssister;
   // @Inject 
    //private DaoGroupe		daoGroupe;
	// Getters & Setters
	
	public ObservableList<Partenaire> getListe() {
		return liste;
	}
	
	/*public ObservableList<Groupe> getListeGroupe() {
		return listeGroupe;
	}*/
	
	public Partenaire getCourant() {
		return courant;
	}
	
	public Partenaire getSelection() {
		return selection;
	}
	
	public void setSelection( Partenaire partenaire ) {
		this.selection = partenaire;
	}
	
	
	//actions
	public void actualiserListe() {
		liste.setAll( daoPartenaire.listePartenaire() );
 	}
	
	/*public void listerGroupe() {
		listeGroupe.setAll(daoGroupe.listerTout());
		for(Groupe g:listeGroupe)System.out.println(g);
	}*/
	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Partenaire();
//			System.out.println(selection);
//			System.out.println("on est en cours d'actualisation dans le if");
		} else {
//			System.out.println(selection);
//			System.out.println("on est en cours d'actualisation dans le else");
			selection = daoPartenaire.retrouver( selection.getNom() );
		}
		mapper.update( courant, selection );
		
	}
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		
		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
			message.append( "\nLe nom ne doit pas être vide." );
		} else  if ( courant.getNom().length()> 30 ) {
			message.append( "\nLe nom est trop long." );
		}

		if( courant.getDescription() == null || courant.getDescription().isEmpty() ) {
			message.append( "\nLa description ne doit pas être vide." );
		} else if ( courant.getDescription().length()> 1000 ) {
			message.append( "\nLa description est trop long." );
		}
		
			
			selection.setNom( daoPartenaire.inserer( courant ) );
			daoAssister.insererPartenairePourEvenement(courant);
		}
	
	
	public void supprimer( Partenaire p ) {
		
		daoPartenaire.supprimer( p.getNom() );
		selection = UtilFX.findNext( liste, p );
	}
	
	public void modifier() {
StringBuilder message = new StringBuilder();
		
		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
			message.append( "\nLe nom ne doit pas être vide." );
		} else  if ( courant.getNom().length()> 30 ) {
			message.append( "\nLe nom est trop long." );
		}

		if( courant.getDescription() == null || courant.getDescription().isEmpty() ) {
			message.append( "\nLa description ne doit pas être vide." );
		} else if ( courant.getDescription().length()> 1000 ) {
			message.append( "\nLa description est trop long." );
		}
		daoAssister.insererPartenairePourEvenement(courant);
		daoPartenaire.modifier(courant);
		
		
		
	}
	


}
