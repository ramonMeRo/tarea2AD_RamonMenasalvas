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
			ps = con.prepareStatement("insert into visitadas(idPeregrino, idParada ) values('28','29')"); ///, fecha) values (?, ?, ?)");
//			ps.setLong(1, peregrino.getId());
//			ps.setLong(2, parada.getId());
		//	Date fecha =Date.valueOf(LocalDate.now());
		//	ps.setDate(3, fecha);
			
			ps.executeUpdate();
			ps.close();
			insertada = true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return insertada;
	}
	
	public Set<Parada> paradasVisitadas(long id){
		Set<Parada> listaParadas = new HashSet<Parada>();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select idParada, fecha from visitadas where idPeregrino = ?");
			ps.setLong(1, id);
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
			ps = con.prepareStatement("select idPeregrino, idParada, fecha from visitadas where idPeregrino = ?");
			ps.setLong(1, peregrino.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Visita visita = new Visita();
				
				visita.setIdPeregrino(rs.getLong("idPeregrino"));
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
	
	public Visita visitaPeregrino(Peregrino peregrino, LocalDate hoy){
		Visita visita = new Visita();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select idParada, fecha from visitadas where idPeregrino = ? and fecha = ?");
			ps.setLong(1, peregrino.getId());
			ps.setDate(2, Date.valueOf(hoy) );
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				visita.setIdPeregrino(peregrino.getId());
				visita.setIdParada(rs.getLong("idParada"));
				visita.setFecha(rs.getDate("fecha").toLocalDate());			
			}
			rs.close();
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return visita;
	}
}
