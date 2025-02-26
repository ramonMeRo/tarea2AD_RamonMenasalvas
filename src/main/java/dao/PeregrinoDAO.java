package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import bd.ConexionBD;
import entidades.Carnet;
import entidades.Parada;
import entidades.Peregrino;
import entidades.Usuario;

public class PeregrinoDAO {

	private static PeregrinoDAO instance;
	Connection con;

	private PeregrinoDAO(Connection con) {
		if (this.con == null)
			this.con = con;
	}

	public static PeregrinoDAO getPeregrinoDAO() {
		if (instance == null)
			instance = new PeregrinoDAO(ConexionBD.getInstance().getConnection());
		return instance;
	}

	public boolean insertarPeregrino(Peregrino peregrino, Usuario user, Carnet carnet, Long id) {
		boolean insertado = false;
		PreparedStatement ps;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(
					"insert into peregrinos (idUsuario, idCarnet, nombreCompleto, nacionalidad) values (?, ?, ?, ?)");
			ps.setLong(1, user.getId());
			ps.setLong(2, carnet.getId());
			ps.setString(3, peregrino.getNombre());
			ps.setString(4, peregrino.getNacionalidad());
			ps.executeUpdate();

			con.commit();
			ps.close();
			insertado = true;

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return insertado;
	}

	public Set<Peregrino> obtenerPeregrinos() {
		Set<Peregrino> listaPeregrinos = new HashSet<Peregrino>();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select * from peregrinos");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Peregrino peregrino = new Peregrino();
				peregrino.setId(rs.getLong("id"));
				peregrino.setNombre(rs.getString("nombreCompleto"));
				peregrino.setNacionalidad(rs.getString("nacionalidad"));
				peregrino.setCarnet(CarnetDAO.getCarnetDAO().obtenerCarnet(rs.getLong("idCarnet")));
				
				listaPeregrinos.add(peregrino);
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaPeregrinos;
	}

	public Peregrino seleccionarPeregrino(long id) {
		Set<Parada> paradas = new HashSet<Parada>();
		Peregrino peregrino = new Peregrino();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select * from peregrinos where id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peregrino.setId(rs.getLong("id"));
				peregrino.setNombre(rs.getString("nombreCompleto"));
				peregrino.setNacionalidad(rs.getString("nacionalidad"));
				peregrino.setCarnet(CarnetDAO.getCarnetDAO().obtenerCarnet(rs.getLong("idCarnet")));
				paradas = VisitadasDAO.getVisitadasDAO().paradasVisitadas(id);
				peregrino.setParadas(paradas);
			}
			rs.close();
			ps.close();
			return peregrino;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Peregrino seleccionarPeregrinoEstancia(long id) {
		Peregrino peregrino = new Peregrino();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select * from peregrinos where id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peregrino.setId(rs.getLong("id"));
				peregrino.setNombre(rs.getString("nombreCompleto"));
				peregrino.setNacionalidad(rs.getString("nacionalidad"));
				peregrino.setCarnet(CarnetDAO.getCarnetDAO().obtenerCarnet(rs.getLong("idCarnet")));
			}
			rs.close();
			ps.close();
			return peregrino;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public long obtenerIdPeregrino() {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select max(id) as id from peregrinos");
			ResultSet rs = ps.executeQuery();

			if (rs.next())
				return rs.getLong("id");

			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Peregrino obtenerPeregrinoConUsuario(Usuario usuario) {
		Peregrino peregrino = new Peregrino();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select * from peregrinos p inner join usuarios u on p.idUsuario = u.id where u.id = ?");
			ps.setLong(1, usuario.getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				peregrino.setId(rs.getLong("id"));
				peregrino.setNombre(rs.getString("nombre"));
				peregrino.setNacionalidad(rs.getString("nacionalidad"));
				peregrino.setCarnet(CarnetDAO.getCarnetDAO().obtenerCarnet(rs.getLong("idCarnet")));
				peregrino.setParadas(VisitadasDAO.getVisitadasDAO().paradasVisitadas(peregrino.getId()));
				peregrino.setEstancias(EstanciaDAO.getEstanciaDAO().obtenerEstanciasDePeregrino(peregrino));
			}
			rs.close();
			ps.close();
			return peregrino;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
