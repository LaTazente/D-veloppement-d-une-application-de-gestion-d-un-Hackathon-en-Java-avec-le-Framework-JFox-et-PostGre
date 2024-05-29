package hackathon.commun;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class Roles {
	
	// Champs statiques
	// GestionnaireParticipants
	public static final String ADMINISTRATEUR	= "ADMINISTRATEUR";
	public static final String UTILISATEUR		= "UTILISATEUR";
	
	public static final String GESTIONNAIREPARTICIPANTS		= "GESTIONNAIREPARTICIPANTS";
	
	private static final List<String>	roles = Collections.unmodifiableList( Arrays.asList( 
			ADMINISTRATEUR,			
			UTILISATEUR,
			GESTIONNAIREPARTICIPANTS
	) );

	private static final String[]	 	libelles = new String[] {
			"Administrateur",
			"Utilisateur",
			"GestionnaireParticipants",
	};
	
	
	// Constructeur privé qui empêche l'instanciation de la classe
	private Roles() {
	}
	
	
	// Actions

	public static List<String> getRoles() {
		return roles;
	}
	
	public static String getLibelle( String role ) {
		int index = roles.indexOf( role );
		if ( index == -1 ) {
			throw new IllegalArgumentException();
		} 
		return libelles[index];
	}

}
