package hackathon.view.gestionNotes;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

//import hackathon.dao.DaoEvaluer;
import hackathon.data.Activite;
import hackathon.data.Groupe;
import hackathon.data.Jury;
import hackathon.data.Note;
import hackathon.report.EnumReport;
import hackathon.report.ManagerReport;
//import hackathon.view.evenement.ModelActivite;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jfox.javafx.control.EditingCell;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControleurGestionDesNotes extends Controller{
	
	@Inject
	private ModelNotation modelNotation;
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ManagerReport	managerReport;
//	@Inject
//	private ModelActivite modelActivite;
	
	
    @FXML
    private ComboBox<Jury> cmbJurys;

    @FXML
    private ComboBox<Activite> cmbDefis;

    @FXML
    private TableView<Note> tbvNotation;

    @FXML
    private TableColumn<Note, Groupe> ColGroupe;

    @FXML
    private TableColumn<Note, String> ColNote;

    @FXML
    private ComboBox<Activite> cmbDefis1;
    

    
	@FXML
	private void initialize() {
		
		tbvNotation.setItems(modelNotation.getListe());
		UtilFX.setValueFactory( ColGroupe, "groupe" );
		UtilFX.setCellFactory(ColGroupe, "nom");
		UtilFX.setValueFactory( ColNote, "note" );
		ColNote.setCellFactory(p->new EditingCell<>());
		
		cmbJurys.setItems(modelNotation.getListeJury());
		UtilFX.setCellFactory(cmbJurys, "id_jury");
		
		cmbDefis.setItems( modelNotation.getListeDefis());
		UtilFX.setCellFactory(cmbDefis, "nom");
		
		cmbDefis1.setItems( modelNotation.getListeDefis());
		UtilFX.setCellFactory(cmbDefis1, "nom");	
	}
	
	@FXML
	public void refresh() {
		modelNotation.listerDefis();
		modelNotation.listerJury();
		tbvNotation.requestFocus();
	}
	
	public void actualiseListe() {
		if(cmbDefis1.getValue() != null)
			modelNotation.listerNote(cmbDefis1.getValue());
	}

    @FXML
    void DoAnnuler(ActionEvent event) {

    }

    @FXML
    void Dolier(ActionEvent event) {
    	if(modelNotation.getJury(cmbDefis.getValue()) == null)
    		modelNotation.insertion(cmbDefis.getValue(), cmbJurys.getValue());
    	else
    		modelNotation.modifierJury(cmbDefis.getValue(), cmbJurys.getValue());
    	
    	managerGui.showDialogMessage(cmbJurys.getValue().getId_jury()+" notera l'activité "+cmbDefis.getValue().getNom());
    	
    	
    }
    
    @FXML 
    void actualiserLiaison() {
    	cmbJurys.setValue(modelNotation.getJury(cmbDefis.getValue()));
    }
    
    @FXML
    public void modifierNote() {
    	//tbvNotation.getSelectionModel().getSelectedItem().setNote(ColNote.getCellData(tbvNotation.getSelectionModel().getSelectedIndex()));
    	modelNotation.ValiderModif(tbvNotation.getItems());
    	modelNotation.listerNote(cmbDefis1.getValue());
    	
    }
    
    @FXML
    private void doResultat() {
     Map<String, Object> params = new HashMap<>();
     params.put( "IdActivite", cmbDefis1.getValue().getId_activite() );
     managerReport.openFilePdf(EnumReport.ResultatActivite,params);
    }


}
