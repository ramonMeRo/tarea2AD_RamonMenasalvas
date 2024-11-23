package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import entidades.Carnet;


public class CarnetDAO {

	public CarnetDAO() {

	}

	public boolean insertarCarnet(Carnet carnet) {

		boolean insertado = false;

		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("insert into carnets "
					+ "(id, idParadaExpedido, fechaExpedido, distancia, numeroVips) values (?,?,?,?,?)");
			ps.setLong(1, carnet.getId());
			ps.setLong(2, carnet.getParadaInicial().getId());
			ps.setDate(3, Date.valueOf(carnet.getFechaExp()));
			ps.setDouble(4, carnet.getDistancia());
			ps.setInt(5, carnet.getnVips());
			ps.executeUpdate();

			ps.close();
			insertado = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return insertado;
	}

	public Carnet obtenerCarnet(long id) {
		ParadaDAO pDAO = new ParadaDAO();
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement(
					"select id, idParadaExpedido, fechaExpedido, distancia, numeroVips from carnets where id = ?");

			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Carnet carnet = new Carnet();
				carnet.setId(rs.getLong("id"));
				carnet.setParadaInicial(pDAO.seleccionarParada(rs.getLong("idParadaExpedido")));
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

	public boolean sellarCarnet(long idP, String estancia) {
		boolean actualizado = false;
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			Carnet carnet = obtenerCarnet(idP);
			ps = con.prepareStatement("update carnets set distancia = ?, numeroVips = ? where id = ?");
			ps.setLong(3, idP);
			ps.setDouble(1, carnet.getDistancia() + 5.00);
			if (estancia.equalsIgnoreCase("si")) {
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
