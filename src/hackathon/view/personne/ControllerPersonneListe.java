package hackathon.view.personne;

import javax.inject.Inject;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Categorie;
import hackathon.data.Personne;
import hackathon.view.EnumView;


public class ControllerPersonneListe extends Controller  {
	
	
	// Composants de la vue
	
	@FXML
	private TableView<Personne>	tbvPersonnes;
	@FXML
	private TableColumn<Personne, Integer> colId;
	@FXML
	private TableColumn<Personne, String> colNom;
	@FXML
	private TableColumn<Personne, Categorie> colCategorie;
	@FXML
	private Button				btnModifier;
	@FXML
	private Button				btnSupprimer;
	
	
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelPersonne		modelPersonne;
	
	
	// Initialisation du controller

	@FXML
	private void initialize() {
		
		// TableView
		tbvPersonnes.setItems( modelPersonne.getListe() );
		UtilFX.setValueFactory( colId, "id" );
		UtilFX.setValueFactory( colNom, c -> Bindings.concat( c.getValue().getNom(), " ", c.getValue().getPrenom() ) );
		UtilFX.setValueFactory( colCategorie, "categorie" );
		UtilFX.setCellFactory( colCategorie, "libelle" );
		
		// Configuraiton des boutons
		tbvPersonnes.getSelectionModel().getSelectedItems().addListener( 
		        (ListChangeListener<Personne>) (c) -> {
		        	configurerBoutons();
		});
    	configurerBoutons();
	}

	
	@Override
	public void refresh() {
		modelPersonne.actualiserListe();
		UtilFX.selectRow( tbvPersonnes, modelPersonne.getSelection() );
		tbvPersonnes.requestFocus();
	}
	
	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelPersonne.setSelection(null);;
		managerGui.showView( EnumView.PersonneForm );
	}
	
	@FXML
	private void doModifier() {
		modelPersonne.setSelection( tbvPersonnes.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.PersonneForm );
	}
	
	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm("Etes-vous sûr de voulir supprimer cette personne ?" ) ) {
			modelPersonne.supprimer( tbvPersonnes.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tbvPersonnes.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
    	if( tbvPersonnes.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifier.setDisable(true);
			btnSupprimer.setDisable(true);
		} else {
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
	}
	
}
