package controlador;

import java.util.HashSet;
import java.util.Set;

import dao.CarnetDAO;
import dao.ParadaDAO;
import dao.PeregrinoDAO;
import dao.UsuarioDAO;
import entidades.Carnet;
import entidades.Estancia;
import entidades.Parada;
import entidades.Peregrino;
import entidades.Perfil;
import entidades.Usuario;

public class ControladorRegistro {

	public static Peregrino RegistrarPeregrino(String nombre, String nombreUsuario, String password, String nacionalidad,
			Long paradaInicial) {

		if (UsuarioDAO.getUsuarioDAO().comprobarUsuario(nombreUsuario) != null) {
			return null;
		}
		
		Usuario usuario = new Usuario();
		usuario.setNombre(nombreUsuario);
		usuario.setPassword(password);
		usuario.setPerfil(Perfil.PEREGRINO);
		UsuarioDAO.getUsuarioDAO().insertarUsuario(usuario);
		usuario.setId(UsuarioDAO.getUsuarioDAO().obtenerIdUsuario());

		Parada p1 = ParadaDAO.getParadaDAO().seleccionarParada(paradaInicial);

		Carnet carnet = new Carnet();
		carnet.setParadaInicial(p1);
		CarnetDAO.getCarnetDAO().insertarCarnet(carnet);
		carnet.setId(CarnetDAO.getCarnetDAO().obtenerIdCarnet());

		Set<Parada> paradas = new HashSet<Parada>();
		paradas.add(p1);
		
		Peregrino peregrino = new Peregrino();
		peregrino.setNombre(nombre);
		peregrino.setNacionalidad(nacionalidad);
		peregrino.setCarnet(carnet);
		peregrino.setEstancias(new HashSet<Estancia>());
		peregrino.setParadas(paradas);
		PeregrinoDAO.getPeregrinoDAO().insertarPeregrino(peregrino, usuario, carnet, paradaInicial);
		peregrino.setId(PeregrinoDAO.getPeregrinoDAO().obtenerIdPeregrino());

		ControladorExportar.ExportarCarnet(peregrino);

		return peregrino;
	}

	public static Parada registrarParada(String nombre, char region, String responsable) {

		if (!ParadaDAO.getParadaDAO().compararParada(nombre, region)) {
			Parada parada = new Parada(ParadaDAO.getParadaDAO().obtenerIdParada(), nombre, region, responsable);
			ParadaDAO.getParadaDAO().insertarParada(parada);
			return parada;
		} else
			return null;
	}

	public static boolean registrarResponsable(String nombre, String password) {
		boolean existe = false;
		String usuario = UsuarioDAO.getUsuarioDAO().comprobarUsuario(nombre);
		if (usuario == null) {
			existe = true;
			Usuario responsable = new Usuario();
			responsable.setId(UsuarioDAO.getUsuarioDAO().obtenerIdUsuario());
			responsable.setNombre(nombre);
			responsable.setPassword(password);
			responsable.setPerfil(Perfil.PARADA);
			UsuarioDAO.getUsuarioDAO().insertarUsuario(responsable);
		}
		return existe;
	}

}
