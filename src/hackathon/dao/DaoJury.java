package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.jdbc.UtilJdbc;
import hackathon.data.Jury;


public class DaoJury {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoPersonne		daoPersonne;

	
	// Actions

	public String inserer( Jury jury ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO jury ( id_jury, nbre_memenbres, code ) VALUES( ?, ?, ?) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, jury.getId_jury() );
			stmt.setObject( 2, jury.getNbre_memenbres() );
			//stmt.setObject( 3, jury.getFlagSiege() );
			//stmt.setObject( 4, jury.getPersonne() == null ? null : jury.getPersonne().getId() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			jury.setId_jury( rs.getObject( 1, Integer.class)+ "");
			return jury.getId_jury();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public void modifier( Jury jury ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE jury SET nbre_memenbres = ?, code = ? WHERE id_jury =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, jury.getNbre_memenbres() );
			// stmt.setObject( 2, jury.getAnneeCreation() );
			//stmt.setObject( 4, jury.getPersonne() == null ? null : jury.getPersonne().getId() );
			stmt.setObject( 3, jury.getId_jury() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( int idJury ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM jury WHERE id_jury = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setInt( 1, idJury );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Jury retrouver( String idJury ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM jury WHERE id_jury = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setString(1, idJury);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireJury( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Jury> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT id_jury, nbre_memenbres, code FROM jury";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			
			List<Jury> liste = new LinkedList<>();
			while (rs.next()) {
				liste.add( construireJury( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
    public int compterPourPersonne( int idPersonne ) {
    	
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM jury WHERE idpersonne = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, idPersonne );
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
	
    protected Jury construireJury( ResultSet rs ) throws SQLException {
		Jury jury = new Jury();
		jury.setId_jury( rs.getObject( "id_jury", String.class ) );
		jury.setNbre_memenbres( rs.getObject( "nbre_memenbres", Integer.class ) );
		
		
		System.out.println(jury);
		String code = rs.getObject( "code", String.class );
		if ( code != null ) {
			code = null;
		    //jury.setPersonne( daoPersonne.retrouver( idPersonne ) );
		}
		return jury;
	}
    
    // cabrel
    public List<Jury> listeJury(){
  		Connection			cn		= null;
  		PreparedStatement	stmt	= null;
  		ResultSet 			rs		= null;
  		String				sql;
  		//System.out.println("on est ici");
  		try {
  			cn = dataSource.getConnection();
  			sql = "SELECT * FROM hackathon.jury";
  			stmt = cn.prepareStatement( sql );
  			rs = stmt.executeQuery();

  			List<Jury> liste = new ArrayList<>();
  			while (rs.next()) {
  				liste.add( construireJury( rs ) );
  			}
  			return liste;

  		} catch (SQLException e) {
  			throw new RuntimeException(e);
  		} finally {
  			UtilJdbc.close( rs, stmt, cn );
  		}	
  	}

}
