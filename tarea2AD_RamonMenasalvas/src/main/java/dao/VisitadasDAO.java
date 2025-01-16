package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import bd.ConexionBD;
import entidades.Parada;
import entidades.Peregrino;
import entidades.Visita;

public class VisitadasDAO {
	
	private static VisitadasDAO instance;
	Connection con;

	private VisitadasDAO(Connection con) {
		if (this.con == null)
			this.con = con;
	}

	public static VisitadasDAO getVisitadasDAO() {
		if (instance == null)
			instance = new VisitadasDAO(ConexionBD.getInstance().getConnection());
		return instance;
	}
	
	public boolean insertarVisita(Peregrino peregrino, Parada parada) {
		boolean insertada = false;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("insert into visitadas (idPeregrino, idParada, fecha) values (?, ?, ?)");
			ps.setLong(1, peregrino.getId());
			ps.setLong(2, parada.getId());
			ps.setDate(3, Date.valueOf(LocalDate.now()));
			
			ps.executeUpdate();
			insertada = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return insertada;
	}
	
	public Set<Parada> paradasVisitadas(Peregrino peregrino){
		Set<Parada> listaParadas = new HashSet<Parada>();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select idParada, fecha from visitadas where idPeregrino = ?");
			ps.setLong(1, peregrino.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Parada parada = new Parada();
				parada = ParadaDAO.getParadaDAO().seleccionarParada(rs.getLong("idParada"));
				listaParadas.add(parada);
			}
			rs.close();
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return listaParadas;
	}
	
	public Set<Visita> visitasPeregrino(Peregrino peregrino){
		Set<Visita> listaVisitas = new HashSet<Visita>();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select idParada, fecha from visitadas where idPeregrino = ?");
			ps.setLong(1, peregrino.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Visita visita = new Visita();
				visita.setIdParada(rs.getLong("idParada"));
				visita.setFecha(rs.getDate("fecha").toLocalDate());
				listaVisitas.add(visita);
			}
			rs.close();
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return listaVisitas;
	}
}
