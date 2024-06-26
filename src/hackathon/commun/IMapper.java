package hackathon.commun;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import hackathon.data.Agir;
import hackathon.data.Categorie;
import hackathon.data.Compte;
import hackathon.data.Evenement;
import hackathon.data.Memo;
import hackathon.data.Partenaire;
import hackathon.data.Participant;
import hackathon.data.Personne;
import hackathon.data.Service;
import hackathon.data.Utilisateur;
import hackathon.data.Groupe;
import hackathon.data.Jury;
import hackathon.data.Membre_jury;


@Mapper
public interface IMapper {
	
	Compte update( @MappingTarget Compte target, Compte source  );
	
	Categorie update( @MappingTarget Categorie target, Categorie source );
	
	// Mes ajouts
	Groupe update( @MappingTarget Groupe target, Groupe source );
	
	Utilisateur update( @MappingTarget Utilisateur target, Utilisateur source );

	//Cabrel
	
	Jury update( @MappingTarget Jury target, Jury source );
	
	Membre_jury update( @MappingTarget Membre_jury target, Membre_jury source );
	
	//Cedric
	Participant update( @MappingTarget Participant target, Participant source );
	
	Partenaire update( @MappingTarget Partenaire target, Partenaire source );
	
	Evenement update( @MappingTarget Evenement target, Evenement source );
	
	@Mapping( target="categorie", expression="java( source.getCategorie() )" )
	Personne update( @MappingTarget Personne target, Personne source );

	@Mapping( target="categorie", expression="java( source.getCategorie() )" )
	Memo update( @MappingTarget Memo target, Memo source );

	@Mapping( target="personne", expression="java( source.getPersonne() )" )
	Service update( @MappingTarget Service target, Service source );
	
	@Mapping( target="memo", expression="java( source.getMemo() )" )
	@Mapping( target="personne", expression="java( source.getPersonne() )" )
	Agir update( @MappingTarget Agir target, Agir source );
	
	
	
	
}
