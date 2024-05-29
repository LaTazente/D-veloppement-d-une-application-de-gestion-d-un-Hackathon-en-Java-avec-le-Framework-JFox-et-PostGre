package hackathon.view.memo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import jfox.javafx.util.ConverterBigDecimal;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.ConverterLocalDate;
import jfox.javafx.util.ConverterLocalTime;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Agir;
import hackathon.data.Categorie;
import hackathon.data.Compte;
import hackathon.data.Memo;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import hackathon.view.categorie.ModelCategorie;


public class ControllerMemoForm extends Controller {

	
	// Composants de la vue

	@FXML
	private TextField		txfId;
	@FXML
	private TextField		txfTitre;
	@FXML
	private TextArea		txaDescription;
	@FXML
	private CheckBox		ckbUrgent;
	@FXML
	private ComboBox<Categorie> cmbCategorie;
	@FXML
	private ToggleGroup		tggStatut;
	@FXML
	private TextField		txfEffectif;
	@FXML
	private TextField		txfBudget;
	@FXML
	private DatePicker		dtpEcheance;
	@FXML
	private TextField		txfHeure;
	
	@FXML
	private ListView<Compte>	lsvAbonnes;
	@FXML
	private Button			btnAbonneSupprimer;
	
	@FXML
	private TableView<Agir>	tbvActeurs;
	@FXML
	private TableColumn<Agir, Personne>	colPersonne;
	@FXML
	private TableColumn<Agir, String>	colFonction;
	@FXML
	private Button			btnActeurModifier;
	@FXML
	private Button			btnActeurSupprimer;
	
	@FXML
	private ImageView		imvSchema;
	@FXML
	private Button			btnSchemaOuvrir;

	
	// Autres champs

	@Inject
	private IManagerGui 	managerGui;
	@Inject
	private ModelMemo 		modelMemo;
	@Inject
	private ModelCategorie	modelCategorie;
	@Inject
	private ModelMemoActeur		modelActeur;

	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		Memo courant = modelMemo.getCourant();

		// Data binding

		bindBidirectional(txfId, courant.idProperty(), new ConverterInteger());
		bindBidirectional(txfTitre, courant.titreProperty());
		bindBidirectional(txaDescription, courant.descriptionProperty());
		bindBidirectional(ckbUrgent, courant.flagUrgentProperty());
		
		// Catégorie
		cmbCategorie.setItems( modelCategorie.getListe() );
		bindBidirectional( cmbCategorie, courant.categorieProperty() );
		UtilFX.setCellFactory( cmbCategorie, "libelle" );

		// Statut
		bindBidirectional( tggStatut, courant.statutProperty(), "A", "E", "F" );

		// Effectif
		bindBidirectional(txfEffectif, courant.effectifProperty(), new ConverterInteger());

		// Budget
		bindBidirectional(txfBudget, courant.budgetProperty(), new ConverterBigDecimal( "#,##0.00" ) );

		// Date d'échéance
		bindBidirectional(dtpEcheance, courant.echeanceProperty(), new ConverterLocalDate());
		
		// Heure
		bindBidirectional(txfHeure, courant.heureProperty(), new ConverterLocalTime());
		
		// Liste Abonnés
		lsvAbonnes.setItems( courant.getAbonnes() );
		lsvAbonnes.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
		
		// Liste Acteurs
		tbvActeurs.setItems( courant.getActeurs() );
		UtilFX.setValueFactory( colPersonne, "personne" );
		UtilFX.setCellFactory( colPersonne, p -> p.getNom() + " " +p.getPrenom() );
		UtilFX.setValueFactory( colFonction, "fonction" );

		// Configuraiton des boutons de l'onglet Acteurs
		lsvAbonnes.getSelectionModel().selectedItemProperty().addListener(
				(obs, nv, ov) -> configurerBoutonsAbonnes()  );
		configurerBoutonsAbonnes();

		tbvActeurs.getSelectionModel().selectedItemProperty().addListener(
				(obs, nv,ov) -> configurerBoutonsActeurs()  );
		configurerBoutonsActeurs();
		
		// ImageView
		bindBidirectional( imvSchema, modelMemo.schemaProperty() );
		
	}

	public void refresh() {
		modelMemo.actualiserCourant();
		
		if ( modelMemo.getCheminSchemaCourant().exists() ) {
			btnSchemaOuvrir.setDisable( false );
		} else {
			btnSchemaOuvrir.setDisable( true );
		}		
	}

	
	// Actions

	@FXML
	private void doAnnuler() {
		managerGui.showView(EnumView.MemoListe);
	}

	@FXML
	private void doValider() {
		modelMemo.validerMiseAJour();
		managerGui.showView(EnumView.MemoListe);
	}
	
	@FXML
	private void doAbonneAjouter() {
		managerGui.showDialog( EnumView.MemoAbonner );
	}

	@FXML
	private void doAbonneSupprimer() {
		var items = lsvAbonnes.getSelectionModel().getSelectedItems();
		modelMemo.getCourant().getAbonnes().removeAll( items );
	}
	
	@FXML
	private void doActeurAjouter() {
		modelActeur.setSelection(null);
		managerGui.showDialog( EnumView.MemoActeur );
	}
	
	@FXML
	private void doActeurModifier() {
		modelActeur.setSelection( tbvActeurs.getSelectionModel().getSelectedItem() );
		managerGui.showDialog( EnumView.MemoActeur );
	}
	
	@FXML
	private void doActeurSupprimer() {
		var item = tbvActeurs.getSelectionModel().getSelectedItem();
		modelMemo.getCourant().getActeurs().remove( item );
	}
	
	@FXML
	private void doSchemaChoisir() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisissez un fichier image");
		File chemin = fileChooser.showOpenDialog( managerGui.getStage() );
		if ( chemin != null ) {
			imvSchema.setImage( new Image( chemin.toURI().toString() ) );
		}	
	}
		
	@FXML
	private void doSchemaOuvrir() {
		try {
			Desktop.getDesktop().open( modelMemo.getCheminSchemaCourant() );
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
	}
		
	@FXML
	private void doSchemaSupprimer() {
		imvSchema.setImage( null );
	}	
	
	// Gestion des évènements

	// Clic sur la liste des acteurs
	@FXML
	private void gererClicSurListeActeurs( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tbvActeurs.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doActeurModifier();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutonsAbonnes() {
		
    	if( lsvAbonnes.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnAbonneSupprimer.setDisable(true);
		} else {
			btnAbonneSupprimer.setDisable(false);
		}
	}
	
	private void configurerBoutonsActeurs() {
		
    	if( tbvActeurs.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnActeurModifier.setDisable(true);
			btnActeurSupprimer.setDisable(true);
		} else {
			btnActeurModifier.setDisable(false);
			btnActeurSupprimer.setDisable(false);
		}
	}

}
