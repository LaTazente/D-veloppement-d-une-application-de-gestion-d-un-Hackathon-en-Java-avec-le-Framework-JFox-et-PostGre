package hackathon.view;

import jfox.javafx.view.IEnumView;
import jfox.javafx.view.View;


public enum EnumView implements IEnumView {

	
	// Valeurs
	
	Info				( "systeme/ViewInfo.fxml" ),
	Connexion			( "systeme/ViewConnexion.fxml" ),
	CompteListe			( "compte/ViewCompteListe.fxml" ),
	CompteForm			( "compte/ViewCompteForm.fxml" ),
	CategorieListe		( "categorie/ViewCategorieListe.fxml" ),
	CategorieForm		( "categorie/ViewCategorieForm.fxml" ),
	PersonneListe		( "personne/ViewPersonneListe.fxml" ),
	PersonneForm		( "personne/ViewPersonneForm.fxml" ),
	PersonneEtatParCategorie( "personne/ViewPersonneEtatParCategorie.fxml" ),
	MemoListe			( "memo/ViewMemoListe.fxml" ),
	MemoForm			( "memo/ViewMemoForm.fxml" ),
	MemoAbonner			( "memo/ViewMemoAbonner.fxml" ),
	MemoActeur			( "memo/ViewMemoActeur.fxml" ),
	ServiceListe		( "service/ViewServiceListe.fxml" ),
	ServiceForm			( "service/ViewServiceForm.fxml" ),
	TestDaoCategorie	( "test/ViewTestDaoCategorie.fxml" ),
	TestDaoMemo			( "test/ViewTestDaoMemo.fxml" ),
	TestDaoService		( "test/ViewTestDaoService.fxml" ),
	EtatPersonneParCategorie1	( "personne/ViewEtatPersonneParCategorie1.fxml" ),
	EtatPersonneParCategorie2	( "personne/ViewEtatPersonneParCategorie2.fxml" ),
	;

	
	// Champs
	
	private final View	view;

	
	// Constructeurs
	
	EnumView( String path, boolean flagReuse ) {
		view = new View(path, flagReuse);
	}
	
	EnumView( String path ) {
		view = new View(path);
	}

	
	// Getters & setters
	
	@Override
	public View getView() {
		return view;
	}
}
