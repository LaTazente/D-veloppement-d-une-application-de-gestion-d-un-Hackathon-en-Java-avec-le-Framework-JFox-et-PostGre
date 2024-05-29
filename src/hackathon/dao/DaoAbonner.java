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
import hackathon.data.Compte;
import hackathon.data.Memo;


public class DaoAbonner {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoCompte		daoCompte;

	
	// Actions

	public void insererPourMemo( Memo memo ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO abonner ( idmemo, idcompte ) VALUES( ?, ? ) ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, memo.getId() );
			for ( Compte compte : memo.getAbonnes() ) {
				stmt.setObject( 2, compte.getId() );
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
			sql = "DELETE FROM abonner WHERE idmemo = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idMemo );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public List<Compte> listerPourMemo( Memo memo ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT c.* FROM compte c JOIN abonner a ON c.idcompte = a.idcompte WHERE a.idmemo = ? ORDER BY pseudo";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, memo.getId() );
			rs = stmt.executeQuery();

			List<Compte> comptes = new ArrayList<>();
			while (rs.next()) {
				comptes.add( daoCompte.construireCompte( rs, false ) );
			}
			return comptes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
}
