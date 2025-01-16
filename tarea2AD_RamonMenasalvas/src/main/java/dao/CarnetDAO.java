package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bd.ConexionBD;

import java.sql.Date;

import entidades.Carnet;

public class CarnetDAO {

	private static CarnetDAO instance;
	private Connection con;

	private CarnetDAO(Connection con) {
		if (this.con == null)
			this.con = con;
	}

	public static CarnetDAO getCarnetDAO() {
		if (instance == null)
			instance = new CarnetDAO(ConexionBD.getInstance().getConnection());
		return instance;
	}

	public boolean insertarCarnet(Carnet carnet) {

		boolean insertado = false;

		PreparedStatement ps;
		try {
			ps = con.prepareStatement("insert into carnets "
					+ "(idParadaExpedido, fechaExpedido, distancia, numeroVips) values (?,?,?,?)");
			ps.setLong(1, carnet.getParadaInicial().getId());
			ps.setDate(2, Date.valueOf(carnet.getFechaExp()));
			ps.setDouble(3, carnet.getDistancia());
			ps.setInt(4, carnet.getnVips());
			ps.executeUpdate();

			ps.close();
			insertado = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insertado;
	}

	public Carnet obtenerCarnet(long id) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"select * from carnets c inner join peregrinos p on c.id = p.idCarnet where c.id = ?");

			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Carnet carnet = new Carnet();
				carnet.setId(rs.getLong("id"));
				carnet.setParadaInicial(ParadaDAO.getParadaDAO().seleccionarParada(rs.getLong("idParadaExpedido")));
				carnet.setFechaExp(rs.getDate("fechaExpedido").toLocalDate());
				carnet.setDistancia(rs.getDouble("distancia"));
				carnet.setnVips(rs.getInt("numeroVips"));

				rs.close();
				ps.close();
				return carnet;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public long obtenerIdCarnet() {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select max(id) as id from carnets");
			ResultSet rs = ps.executeQuery();
			if(rs.next())
				return rs.getLong("id");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean actualizarCarnet(long idP, boolean vip) {
		boolean actualizado = false;
		PreparedStatement ps;
		try {
			Carnet carnet = obtenerCarnet(idP);
			ps = con.prepareStatement("update carnets set distancia = ?, numeroVips = ? where id = ?");
			ps.setLong(3, idP);
			ps.setDouble(1, carnet.getDistancia() + 5.00);
			if (vip) {
				ps.setInt(2, carnet.getnVips() + 1);
			} else
				ps.setInt(2, carnet.getnVips());
			ps.executeUpdate();
			ps.close();

			actualizado = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actualizado;
	}
}
