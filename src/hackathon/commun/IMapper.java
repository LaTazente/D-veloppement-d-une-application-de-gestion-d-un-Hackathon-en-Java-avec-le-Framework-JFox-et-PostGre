package hackathon.commun;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import hackathon.data.Agir;
import hackathon.data.Categorie;
import hackathon.data.Compte;
import hackathon.data.Memo;
import hackathon.data.Personne;
import hackathon.data.Service;


@Mapper
public interface IMapper {
	
	Compte update( @MappingTarget Compte target, Compte source  );
	
	Categorie update( @MappingTarget Categorie target, Categorie source );

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
