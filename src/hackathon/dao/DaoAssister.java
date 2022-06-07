package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.data.Activite;
import hackathon.data.Compte;
import hackathon.data.Evenement;
import hackathon.data.Groupe;
import hackathon.data.Partenaire;
import jfox.jdbc.UtilJdbc;

public class DaoAssister {
	
	@Inject
	private DataSource		dataSource;
	
	//Actions
	public void insererPartenairePourEvenement(Partenaire partenaire)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO assister (nom_partenaire, code) VALUES (?,?)";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, partenaire.getNom() );
			stmt.setObject( 2, partenaire.getCode().getCode() );
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

}
