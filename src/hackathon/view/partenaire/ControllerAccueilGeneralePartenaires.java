package hackathon.view.partenaire;

import javax.inject.Inject;

import hackathon.view.EnumView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import jfox.javafx.view.IManagerGui;

public class ControllerAccueilGeneralePartenaires {

	@Inject
	private IManagerGui			managerGui;
	
	 @FXML
	    void goUsers(ActionEvent event) {
	    	managerGui.showView(EnumView.GestionPartenaires);
	    }
}
