package hackathon.view.memo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jfox.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import hackathon.commun.IMapper;
import hackathon.dao.DaoCompte;
import hackathon.dao.DaoMemo;
import hackathon.data.Compte;
import hackathon.data.Memo;
import hackathon.view.systeme.ModelConfig;


public class ModelMemo  {
	
	
	// Données observables 
	
	private final ObservableList<Memo> liste = FXCollections.observableArrayList(); 
	
	private final Memo	courant = new Memo();
	
	private final ObservableList<Compte> comptesAbonnables = FXCollections.observableArrayList(); 
	
	private final Property<Image>	schema = new SimpleObjectProperty<>();


	
	// Autres champs
	
	private Memo		selection;
	
	private  boolean	flagModifSchema;

	@Inject
	private IMapper		mapper;
    @Inject
	private DaoMemo		daoMemo;
    @Inject
	private DaoCompte	daoCompte;
    @Inject
    private ModelConfig	modelConfig;
	
	
	// Getters & Setters
	
	public ObservableList<Memo> getListe() {
		return liste;
	}
	
	public Memo getCourant() {
		return courant;
	}
	
	public Memo getSelection() {
		return selection;
	}
	
	public void setSelection( Memo selection ) {
		this.selection = selection;
	}
	
	public ObservableList<Compte> getComptesAbonnables() {
		return comptesAbonnables;
	}
	
	public Property<Image> schemaProperty() {
		return schema;
	}	

	
	// Initialisations

	@PostConstruct
	public void init() {
		schema.addListener( obs -> flagModifSchema = true );
	}	
	
	
	// Actions
	
	public void actualiserListe() {
		liste.setAll( daoMemo.listerTout() );
 	}
	
	public void actualiserComptesAbonnables() {
		comptesAbonnables.setAll( daoCompte.listerTout() );
		comptesAbonnables.removeAll( courant.getAbonnes() );
 	}

	
	public void actualiserCourant() {
		if ( selection == null ) {
			selection = new Memo();
			selection.setFlagUrgent(false);
			selection.setStatut( "A" );
		} else {
			selection = daoMemo.retrouver( selection.getId() );
		}
		mapper.update( courant, selection );
		
		File chemin = getCheminSchemaCourant();
		if ( chemin.exists() ) {
			schema.setValue( new Image( chemin.toURI().toString() ) );
		} else {
			schema.setValue( null );
		}		
		flagModifSchema = false;
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getTitre() == null || courant.getTitre().isEmpty() ) {
			message.append( "\nLe titre ne doit pas être vide." );
		} else  if ( courant.getTitre().length()> 50 ) {
			message.append( "\nLe titre est trop long : 50 maxi." );
		}

		if( courant.getEffectif() != null  ) {
			if( courant.getEffectif() < 0  ) {
				message.append( "\nL'effectif ne peut pas être inférieur à zéro." );
			} else  if ( courant.getEffectif() > 1000 ) {
				message.append( "\nEffectif trop grand : 1000 maxi." );
			}
		}

		if( courant.getBudget() != null  ) {
			if( courant.getBudget().doubleValue() < 0  ) {
				message.append( "\nLe budget ne peut pas être inférieur à zéro." );
			} else  if ( courant.getBudget().doubleValue() > 1000000 ) {
				message.append( "\nBudget trop grand : 1 000 000 maxi." );
			}
		}

		if( courant.getEcheance() != null  ) {
			if( courant.getEcheance().isBefore( LocalDate.of(2000, 1, 1) ) 
					|| courant.getEcheance().isAfter( LocalDate.of(2099, 12, 31) ) ) {
				message.append( "\nLa date d'échéance doit être comprise entre le 01/01/2000 et le 31/12/2099." );
			}
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			selection.setId( daoMemo.inserer( courant ) );
		} else {
			// modficiation
			daoMemo.modifier( courant );
		}
		
		if ( flagModifSchema ) {
			if ( schema.getValue() == null ) {
				getCheminSchemaCourant().delete();
			} else {
				try {
					ImageIO.write( SwingFXUtils.fromFXImage( schema.getValue(), null),
					        "JPG", getCheminSchemaCourant() );
				} catch (IOException e) {
					throw new RuntimeException(e);
				}			
			}
		}
		if ( schema.getValue() == null ) {
			getCheminSchemaCourant().delete();
		} else {
			try {
				ImageIO.write( SwingFXUtils.fromFXImage( schema.getValue(), null),
				        "JPG", getCheminSchemaCourant() );
			} catch (IOException e) {
				throw new RuntimeException(e);
			}			
		}
	}
	
	
	public void supprimer( Memo item ) {
		daoMemo.supprimer( item.getId() );
		selection = UtilFX.findNext( liste, item );
		getCheminSchemaCourant().delete();
	}
	
	
	// Méthodes auxiliaires

	public File getCheminSchemaCourant() {
		String nomFichier = String.format( "%06d.jpg", courant.getId() );
		File dossierSchemas = modelConfig.getDossierSchemas();
		return new File( dossierSchemas, nomFichier );
	}
	
}
