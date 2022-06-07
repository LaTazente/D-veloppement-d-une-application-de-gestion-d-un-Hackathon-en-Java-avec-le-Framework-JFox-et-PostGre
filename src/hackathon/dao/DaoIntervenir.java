package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.jdbc.UtilJdbc;
import hackathon.data.Utilisateur;

public class DaoIntervenir {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public void insererPourUtilisateur( Utilisateur utilisateur, List<String> tab )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO intervenir (id_user,code, role) VALUES (?,?,?)";
			stmt = cn.prepareStatement( sql );
			
			for( String role : tab ) {
				stmt.setObject( 1, utilisateur.getId_user() );
				//stmt.setObject( 2, "hack1" );
				stmt.setObject( 2, utilisateur.getCode() == null ? null : utilisateur.getCode().getCode());
				stmt.setObject( 3, role );
				stmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public void supprimerPourUtilisateur( int idUtilisateur ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime les roles
			sql = "DELETE FROM intervenir  WHERE idutilisateur = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idUtilisateur );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public List<String> listerPourUtilisateur( Utilisateur utilisateur ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM intervenir WHERE id_user = ? ORDER BY role";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, utilisateur.getId_user() );
			rs = stmt.executeQuery();

			List<String> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( rs.getObject("role", String.class) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	// pour gerer datasource
	
	
	public Utilisateur verifierUtilisateurParHackathon( Utilisateur utilisateur, String evenement )  {
		
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;
	
		try {
			cn = dataSource.getConnection();
	
			sql = "SELECT * FROM intervenir WHERE id_user = ? AND code = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, utilisateur.getId_user() );
			stmt.setObject( 2, evenement );
			rs = stmt.executeQuery();
	
			if ( rs.next() ) {
				return utilisateur;			
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	

}
