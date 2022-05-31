package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.jdbc.UtilJdbc;
import hackathon.data.Groupe;


public class DaoGroupe {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoJury			daoJury;
	
	// Actions

	public String inserer( Groupe groupe ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO groupe (id_groupe, nom, nbre_menbres, id_jury ) VALUES( ?, ?, ?, ? ) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, groupe.getId_groupe() );
			stmt.setObject( 2, groupe.getNom() );
			// stmt.setObject( 2, groupe.getAnneeCreation() );
			stmt.setObject( 3, groupe.getNbre_menbres() );
			stmt.setObject( 4, groupe.getId_jury() == null ? null : groupe.getId_jury().getId_jury() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			groupe.setId_groupe( rs.getObject( 1, String.class) );
			return groupe.getId_groupe();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public void modifier( Groupe groupe ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			
			sql = "UPDATE groupe SET nom = ?, nbre_menbres = ?, id_jury = ? WHERE id_groupe =  ?";
			stmt = cn.prepareStatement( sql );
			
			stmt.setObject( 1, groupe.getNom() );
			// stmt.setObject( 2, groupe.getAnneeCreation() );
			stmt.setObject( 2, groupe.getNbre_menbres() );
			stmt.setObject( 3, groupe.getId_jury() == null ? null : groupe.getId_jury().getId_jury() );
			stmt.setObject( 4, groupe.getId_groupe() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( String idGroupe ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM groupe WHERE id_groupe = ? ";
			// System.out.println("supprimer");
			stmt = cn.prepareStatement( sql );
			stmt.setString( 1, idGroupe );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Groupe retrouver( String idGroupe ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM groupe WHERE id_groupe = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setString(1, idGroupe);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireGroupe( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	public Groupe retrouverChefParGroupe( String idGroupe ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM PARTICIPANT INNER JOIN groupe ON groupe.ID_GROUPE  = PARTICIPANT.ID_GROUPE WHERE PARTICIPANT.role = 'chef' AND groupe.ID_GROUPE  = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setString(1, idGroupe);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireGroupe( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	public List<Groupe> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM groupe ORDER BY nom";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Groupe> liste = new LinkedList<>();
			while (rs.next()) {
				liste.add( construireGroupe( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
    public int compterPourJury( int idJury ) {
    	
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM groupe WHERE id_jury = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, idJury );
            rs = stmt.executeQuery();

            rs.next();
            return rs.getInt( 1 );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
    }
	
	
	// Méthodes auxiliaires
	
    protected Groupe construireGroupe( ResultSet rs ) throws SQLException {
		Groupe groupe = new Groupe();
		groupe.setId_groupe( rs.getObject( "id_groupe", String.class ) );
		groupe.setNom( rs.getObject( "nom", String.class ) );
		groupe.setNbre_menbres( rs.getObject( "nbre_menbres", Integer.class ) );
		// groupe.setFlagSiege( rs.getObject( "flagsiege", Boolean.class ) );
		String idJury = rs.getObject( "id_jury", String.class );
		if ( idJury != null ) {
		    groupe.setId_jury( daoJury.retrouver( idJury ) );
		}
		return groupe;
	}

}
