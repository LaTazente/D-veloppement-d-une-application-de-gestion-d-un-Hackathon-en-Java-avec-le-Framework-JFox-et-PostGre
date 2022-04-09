package hackathon.view.personne;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import jfox.javafx.control.EditingCell;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Categorie;
import hackathon.data.Personne;
import hackathon.data.Telephone;
import hackathon.view.EnumView;
import hackathon.view.categorie.ModelCategorie;


public class ControllerPersonneForm extends Controller  {
	
	
	// Composants de la vue
	
	@FXML
	private TextField			txfId;
	@FXML
	private TextField			txfNom;
	@FXML	
	private TextField			txfPrenom;
    @FXML
    private ComboBox<Categorie>	cmbCategorie;
	@FXML
	private TableView<Telephone>	tbvTelphones;
	@FXML
	private TableColumn<Telephone, Integer> colId;
	@FXML
	private TableColumn<Telephone, String> colLibelle;
	@FXML
	private TableColumn<Telephone, String> colNumero;

	
	// Autres champs
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelPersonne		modelPersonne;
	@Inject
	private ModelCategorie		modelCategorie;
    
	
	// Initialisation du controller
	
	public void initialize() {

		Personne courant = modelPersonne.getCourant();
		
		// Champs simples
		bindBidirectional( txfId, courant.idProperty(), new ConverterInteger() );
		bindBidirectional( txfNom, courant.nomProperty() );
		bindBidirectional( txfPrenom, courant.prenomProperty() );
        
		// Combo box
		cmbCategorie.setItems( modelCategorie.getListe() );
		bindBidirectional( cmbCategorie, courant.categorieProperty() );
        UtilFX.setCellFactory(cmbCategorie, "libelle" );
		
		// TableView
		tbvTelphones.setItems(  modelPersonne.getCourant().getTelephones() );
		
		UtilFX.setValueFactory( colId, "id" );
		UtilFX.setValueFactory( colLibelle, "libelle" );
		UtilFX.setValueFactory( colNumero, "numero" );

		colLibelle.setCellFactory(  p -> new EditingCell<>() );
		colNumero.setCellFactory(  p -> new EditingCell<>() );		
	
	}
	
	
	@Override
	public void refresh() {
		modelCategorie.actualiserListe();
		modelPersonne.actualiserCourant();
	}
	
	
	// Actions
	
	@FXML
	private void doValider() {
		modelPersonne.validerMiseAJour();
		managerGui.showView( EnumView.PersonneListe );
	}
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.PersonneListe );
	}
	
	@FXML
	private void doAjouterTelephone() {
		modelPersonne.getCourant().getTelephones().add( new Telephone() );
	}
	
	
	@FXML
	private void doiSupprimerTelephone() {
		var telephone = tbvTelphones.getSelectionModel().getSelectedItem();
		modelPersonne.getCourant().getTelephones().remove( telephone );

	}
    
}
