package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import entidades.Parada;
import entidades.Peregrino;

public class VisitadasDAO {

	public VisitadasDAO() {
		
	}
	
	public boolean insertarVisita(Peregrino peregrino, Parada parada) {
		boolean insertada = false;
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("insert into visitadas (idPeregrino, idParada) values (?, ?)");
			ps.setLong(1, peregrino.getId());
			ps.setLong(2, parada.getId());
			
			ps.executeUpdate();
			insertada = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return insertada;
	}
	
	public Set<Parada> paradasVisitadas(Peregrino peregrino){
		Set<Parada> listaParadas = new HashSet<Parada>();
		ParadaDAO pDAO = new ParadaDAO();
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("select idParada from visitadas where idPeregrino = ?");
			ps.setLong(1, peregrino.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Parada parada = new Parada();
				parada = pDAO.obtenerParada(rs.getLong("idParada"));
				listaParadas.add(parada);
			}
			rs.close();
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return listaParadas;
	}
}
