package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.jdbc.UtilJdbc;
import hackathon.data.Agir;
import hackathon.data.Memo;


public class DaoAgir {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoPersonne		daoPersonne;

	
	// Actions

	public void insererPourMemo( Memo memo ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO agir ( idmemo, idpersonne, fonction, debut ) VALUES( ?, ?, ?, ? ) ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, memo.getId() );
			for ( Agir agir : memo.getActeurs() ) {
				stmt.setObject( 2, agir.getPersonne().getId() );
				stmt.setObject( 3, agir.getFonction() );
				stmt.setObject( 4, agir.getDebut() );
				stmt.executeUpdate();
			}
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimerPourMemo( int idMemo ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM agir WHERE idmemo = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idMemo );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public List<Agir> listerPourMemo( Memo memo ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT p.*, a.* FROM personne p JOIN agir a ON p.idpersonne = a.idpersonne WHERE a.idmemo = ? ORDER BY p.nom, p.prenom";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, memo.getId() );
			rs = stmt.executeQuery();

			List<Agir> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireAgir( rs, memo ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	protected Agir construireAgir( ResultSet rs, Memo memo ) throws SQLException {
		Agir agir = new Agir();
		agir.setMemo(memo);
		agir.setPersonne( daoPersonne.construirePersonne( rs, false ) );
		agir.setFonction( rs.getObject( "fonction", String.class ) );
		agir.setDebut( rs.getObject( "debut", LocalDate.class ) );
		return agir;
	}
	
}
