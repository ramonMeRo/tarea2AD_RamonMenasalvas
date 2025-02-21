package utiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.Set;

import dao.CarnetDAO;
import dao.ParadaDAO;
import dao.PeregrinoDAO;
import dao.UsuarioDAO;
import dao.VisitadasDAO;
import entidades.Carnet;
import entidades.Parada;
import entidades.Peregrino;
import entidades.Usuario;
import entidades.Visita;

public class Utiles {

	public static boolean existeParada(String nombre, char region) {
		return ParadaDAO.getParadaDAO().compararParada(nombre, region);
	}

	public static boolean existeParadaPorID(long id) {
		Parada parada = null;
		parada = ParadaDAO.getParadaDAO().seleccionarParada(id);
		if (parada.getId() != null)
			return true;
		else
			return false;
	}

	public static boolean existeUsuario(String nombreUsuario) {
		if (UsuarioDAO.getUsuarioDAO().obtenerUsuario(nombreUsuario) != null) {
			return true;
		}
		return false;
	}

	public static boolean existePeregrino(long id) {
		Peregrino peregrino = PeregrinoDAO.getPeregrinoDAO().seleccionarPeregrino(id);
		if (peregrino != null)
			return true;
		else
			return false;
	}

	public static Peregrino obtenerPeregrino(long id) {
		Peregrino peregrino = PeregrinoDAO.getPeregrinoDAO().seleccionarPeregrino(id);
		return peregrino;
	}

	public static boolean carnetPeregrinoSellado(Peregrino p, Usuario user, LocalDate fecha) {
		Set<Visita> lista = VisitadasDAO.getVisitadasDAO().visitasPeregrino(p);
		Parada paradaActual = ParadaDAO.getParadaDAO().obtenerParada(user);
		for (Visita visita : lista) {
			if (paradaActual.getId().equals(visita.getIdParada()) && visita.getFecha().equals(fecha)
					&& p.getId().equals(visita.getIdPeregrino()))
				return true;
		}
		return false;
	}

	public static Set<Parada> mostrarParadas() {
		Set<Parada> lista = ParadaDAO.getParadaDAO().obtenerParadas();
		return lista;
	}

	public static void mostrarPeregrinos() {
		Set<Peregrino> lista = PeregrinoDAO.getPeregrinoDAO().obtenerPeregrinos();
		for (Peregrino peregrino : lista) {
			System.out.println("ID de peregrino: " + peregrino.getId() + " nombre: " + peregrino.getNombre());
		}
	}

	public static boolean fechaValida(String fecha) {

		if (!fecha.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			return false;
		} else {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			try {
				LocalDate.parse(fecha, dtf);
				return true;
			} catch (DateTimeParseException e) {
				return false;
			}
		}
	}

	public static boolean leerBoolean() {
		boolean ret;

		char resp;
		do {
			System.out.println("Pulse s para Sí o n para No");
			Scanner in = new Scanner(System.in);
			resp = in.nextLine().charAt(0);
			if (resp != 's' && resp != 'S' && resp != 'n' && resp != 'N') {
				System.out.println("Valor introducido incorrecto.");
			}
		} while (resp != 's' && resp != 'S' && resp != 'n' && resp != 'N');
		if (resp == 's' || resp == 'S') {
			return true;
		} else {
			ret = false;
		}
		return ret;
	}

	public static Carnet conseguirCarnet(Peregrino p) {
		return CarnetDAO.getCarnetDAO().obtenerCarnet(p.getId());
	}

}
