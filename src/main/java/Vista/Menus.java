package Vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import controlador.ControladorExportar;
import controlador.ControladorLogin;
import controlador.ControladorNaciones;
import controlador.ControladorRegistro;
import controlador.ControladorSellos;
import dao.ParadaDAO;
import dao.PeregrinoDAO;
import dao.UsuarioDAO;
import dao.VisitadasDAO;
import entidades.Carnet;
import entidades.Parada;
import entidades.Peregrino;
import entidades.Perfil;
import entidades.Usuario;
import utiles.Utiles;

public class Menus {

	private static Usuario actual;

	public static void MenuInvitado() {
		int opc = -1;
		Scanner leer = new Scanner(System.in);
		do {
			System.out.println("Bienvenido al menu de Invitado por favor elija una opcion:");
			System.out.println("1. Login");
			System.out.println("2. Registrarse como Peregrino");
			System.out.println("0. Salir");
			opc = leer.nextInt();
			leer.nextLine();

			switch (opc) {

			case 1:
				System.out.println("--Login--");
				do {
					System.out.println("Introduzca su nombre de usuario:");
					String nombreUsuario = leer.next();
					System.out.println("Introduzca su contraseña:");
					String password = leer.next();

					actual = ControladorLogin.loginAdmin(nombreUsuario, password);
					if (actual != null)

						MenuAdmin();
					else {

						actual = UsuarioDAO.getUsuarioDAO().obtenerUsuario(nombreUsuario);

						if (actual != null && password.equals(actual.getPassword())) {
							if (actual.getPerfil().equals(Perfil.PEREGRINO))
								MenuPeregrino();
							else if (actual.getPerfil().equals(Perfil.PARADA))
								MenuResponsable();

						} else {
							actual = new Usuario();
							System.out.println("Usuario y/o contraseña incorrectos");
						}
					}
				} while (actual.getPerfil().equals(Perfil.INVITADO));

				break;
			case 2:
				String nombreUsuarioP = "";
				String passwordP = "";
				int nacionalidad = 0;
				Peregrino peregrino = null;
				do {
					System.out.println("--Registro--");
					System.out.println("Introduzca nombre de usuario:"
							+ "tenga en cuenta que un nombre compuesto no debe tener espacios");

					boolean valido = false;
					do {
						nombreUsuarioP = leer.nextLine();
						String usuario = UsuarioDAO.getUsuarioDAO().comprobarUsuario(nombreUsuarioP);
						if (usuario != null) {
							System.out.println("Nombre de usuario en uso, escoja otro");
						} else {
							if (nombreUsuarioP.contains(" ")) {
								System.out.println("Nombre con espacios en blanco!!!");

							} else
								valido = true;
						}
					} while (!valido);

					valido = false;
					do {
						System.out.println("Introduzca una contraseña (sin espacios)");
						passwordP = leer.nextLine();
						if (passwordP.length() < 1 || passwordP.contains(" ")) {
							System.out.println(
									"La contraseña no puede contener espacios en blanco y ha de ser distinto de vacio.");
						} else
							valido = true;

					} while (!valido);

					valido = false;
					String nombre = "";
					do {
						System.out.println("Introduce tu nombre completo");
						nombre = leer.nextLine();
						if (nombre.isBlank()) {
							System.out.println("Tienes que escribir un nombre");
						}
						valido = true;
					} while (!valido);

					valido = false;
					String nombreNacion = "";
					do {
						try {
							System.out.println("Introduce la nacionalidad del peregrino:");
							ControladorNaciones.MostrarNaciones();
							nacionalidad = leer.nextInt();

							nombreNacion = ControladorNaciones.ComprobarNacion(nacionalidad);
							if (nombreNacion == null) {
								System.out.println("La nacionalidad introducida no es valida, introduce otra:");
								ControladorNaciones.MostrarNaciones();
								nacionalidad = leer.nextInt();
							}
							valido = true;
						} catch (InputMismatchException e) {
							System.out.println("Solo se permiten numero enteros");
							leer.nextLine();

						}
					} while (!valido);

					valido = false;
					Long paradaIni = 0l;
					Parada paradaInicial = null;
					
					do {
						try {
							Set <Parada> paradas = Utiles.mostrarParadas();
							for (Parada parada : paradas) {
								System.out.println(parada.toString());
							}
							System.out.println("Introduce el id de la parada en la que estas:");	
							paradaIni = leer.nextLong();
							 paradaInicial = ParadaDAO.getParadaDAO().seleccionarParada(paradaIni);
							
						} catch (InputMismatchException e) {
							System.out.println("Solo se permiten numeros enteros");
							leer.nextLine();
						}
						if(Utiles.existeParadaPorID(paradaIni)) {
						
							valido = true;
						} else
							System.out.println("Parada no reconocida");
					} while (!valido);
					peregrino = ControladorRegistro.RegistrarPeregrino(nombre, nombreUsuarioP, passwordP, nombreNacion,
							paradaIni);
					VisitadasDAO.getVisitadasDAO().insertarVisita(peregrino, paradaInicial);
					if (peregrino != null) {
						System.out.println("¡Registro exitoso!");
						System.out.println(peregrino.toString());

					} else {
						System.out.println("El nombre de usuario ya está en uso. Por favor, elija otro.");
					}

				} while (peregrino == null);
				MenuPeregrino();
				break;
			case 0:
				System.out.println("Hasta la proxima");
				break;
			default:
				System.out.println("Opcion invalida, escoja una opcion valida");
				break;
			}
		} while (opc != 0);
		leer.close();
	}

	public static void MenuAdmin() {
		int opc = -1;
		Scanner leer = new Scanner(System.in);
		do {
			System.out.println("Bienvenido al menú de Admin, por favor elija una opción:");
			System.out.println("1. Introducir nueva Parada y Responsable");
			System.out.println("0. Logout");
			opc = leer.nextInt();

			switch (opc) {
			case 1:
				String nombreParada;
				char region;
				boolean existe;
				do {
					System.out.println("--Registro de Parada y Responsable--");
					System.out.println("Introduzca el nombre de la Parada:");
					nombreParada = leer.next();
					System.out.println("Introduzca la región de la Parada:");
					region = leer.next().charAt(0);
					existe = Utiles.existeParada(nombreParada, region);
					if (existe)
						System.out.println("Error ya existe esa parada");
				} while (existe);

				String nombreResponsable;
				String password;
				boolean registrado;
				do {
					boolean valido = false;
					do {
						System.out.println("Introduzca el nombre del Responsable:"
								+ "tenga en cuenta que un nombre compuesto no debe tener espacios");
						nombreResponsable = leer.next();
						Usuario usuario = UsuarioDAO.getUsuarioDAO().obtenerUsuario(nombreResponsable);
						leer.nextLine();
						if (usuario != null) {
							System.out.println("El nombre de usuario esta en uso");
						} else if (nombreResponsable.contains(" ")) {
							System.out.println("El nombre de usuario puede tener espacios en blanco");
						} else
							valido = true;
					} while (!valido);

					valido = false;
					do {
						System.out.println("Introduzca la contraseña del Responsable:");
						password = leer.next();

						if (password.contains(" ")) {
							System.out.println("La contraseña no puede tener espacios en blanco");
						} else
							valido = true;
					} while (!valido);
					registrado = ControladorRegistro.registrarResponsable(nombreResponsable, password);
					if (!registrado) {
						System.out.println("Error ya existe ese nombre de usuario");
					}
				} while (!registrado);

				ControladorRegistro.registrarParada(nombreParada, region, nombreResponsable);
				break;
			case 0:
				System.out.println("Hasta la proxima.");
				MenuInvitado();
				break;
			default:
				System.out.println("Escoja una opcion valida.");

			}
		} while (opc != 0);
		leer.close();
	}

	public static void MenuPeregrino() {
		int opc = -1;
		boolean valido = false;
		Scanner leer = new Scanner(System.in);
		do {
			System.out.println("--Menu Peregrino--");
			System.out.println("1. Exportar Carnet manualmente");
			System.out.println("0. Logout");
			opc = leer.nextInt();

			switch (opc) {
			case 1:
				do {
					System.out.println("Desea exportar su carnet? si/no");
					String confirm = leer.next();
					if (confirm.equalsIgnoreCase("si")) {
						Peregrino p = PeregrinoDAO.getPeregrinoDAO().obtenerPeregrinoConUsuario(actual);
						ControladorExportar.ExportarCarnet(p);
						System.out.println("Carnert exportado");
						valido = true;
					} else if (confirm.equalsIgnoreCase("no")) {
						break;
					} else
						System.out.println("Opcion invalida");
				} while (!valido);

				break;
			case 0:
				System.out.println("Hasta la proxima.");
				MenuInvitado();
				break;
			default:
				System.out.println("Escoja una opcion valida.");
			}
		} while (opc != 0);
		leer.close();
	}

	public static void MenuResponsable() {
		int opc = -1;
		Scanner leer = new Scanner(System.in);
		do {
			System.out.println("--Menu Responsable--");
			System.out.println("1. Sellar Carnet");
			System.out.println("2. Exportar parada");
			System.out.println("0. Logout");
			opc = leer.nextInt();
			long idPeregrino = 0l;
			boolean valido = false;
			LocalDate fechaInicio = null;
			LocalDate fechaFin = null;
			switch (opc) {
			case 1:
				leer.nextLine();
				System.out.println("Sellar carnet/alojarse");
				boolean seguro = false;
				Presentaciones.mostrarParada(actual);
				do {
					System.out.println("introduce el id del peregrino");
					Utiles.mostrarPeregrinos();
					try {
						idPeregrino = leer.nextLong();

					} catch (InputMismatchException e) {
						System.out.println("solo se permiten enteros");
						leer.nextLine();
					}
					if (!Utiles.existePeregrino(idPeregrino)) {
						System.out.println("El peregrino no existe introduce otro id");
					} else {
						valido = true;
					}

				} while (!valido);
				valido = false;
				Peregrino peregrino = Utiles.obtenerPeregrino(idPeregrino);
				Carnet carnet = Utiles.conseguirCarnet(peregrino);
				System.out.println(peregrino.toString()+"\n");
				System.out.println(carnet.toString());
				boolean sellado = Utiles.carnetPeregrinoSellado(peregrino, actual, LocalDate.now());
				if (!sellado) {
					System.out.println("Primera vez en esta parada");
					System.out.println("Se estancia el peregrino? si/no");
					boolean respuesta = Utiles.leerBoolean();
					do {

						if (!respuesta) {
							System.out.println("No se estancia");
							break;
						} else
							System.out.println("Va a ser una estacia vip? si/no");
						boolean respuestaVip = Utiles.leerBoolean();
						System.out.println(respuestaVip);
						if (respuestaVip) {
							ControladorSellos.guardarEstancia(peregrino, actual, respuestaVip, fechaFin);
							ControladorSellos.sellarCarnet(peregrino, respuestaVip);

							if (!ControladorSellos.sellarCarnet(peregrino, respuestaVip)) {
								System.out.println("Ha ocurrido un error");
								break;
							}
						} else {
							ControladorSellos.sellarCarnet(peregrino, respuesta);
						}
						System.out.println("Carnet sellado y actualizado "+peregrino.getCarnet().toString());
						valido = true;
					} while (!valido);
					valido = false;
				} else
					System.out.println(
							"Este peregrino ya estuvo hoy en la parada, por la tanto no puede ser sellado hoy");
				break;

			case 2:
				leer.nextLine();
				System.out.println("Exportar parada");
				seguro = false;
				boolean afirma = false;
				do {
					Presentaciones.mostrarParada(actual);
					do {
						System.out.println("Introduzca una fecha inicial yyyy-MM-dd");
						String primeraFecha = leer.nextLine();
						if (Utiles.fechaValida(primeraFecha)) {
							fechaInicio = LocalDate.parse(primeraFecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
							valido = true;
						} else
							System.out.println("fecha no valida");
					} while (!valido);

					valido = false;
					do {
						System.out.println(
								"Introduzca una fecha final yyyy-mm-dd, si queda en blanco se pondra la fecha de hoy");
						String segundaFecha = leer.nextLine();

						if (Utiles.fechaValida(segundaFecha)) {
							fechaFin = LocalDate.parse(segundaFecha);
						} else if (fechaInicio.isAfter(fechaFin)) {
							System.out.println("La fecha final no puede ser anterior a la fecha de inicio");
						} else if (!Utiles.fechaValida(segundaFecha)) {
							System.out.println("formato de fecha incorrecto");
						} else {
							valido = true;
						}

						if (segundaFecha.isBlank()) {
							fechaFin = LocalDate.now();
						} else if (fechaInicio.isAfter(fechaFin)) {
							System.out.println("La fecha final no puede ser anterior a la fecha de inicio");
						} else {
							valido = true;
						}
					} while (!valido);

					System.out.println(
							"Fecha inicial: " + fechaInicio + "\nFecha final: " + fechaFin + ". ¿Esta seguro? (si/no)");
					do {
						String respuesta = leer.next();
						if (respuesta.equalsIgnoreCase("si")) {
							afirma = true;
						} else if (respuesta.equalsIgnoreCase("no")) {
							afirma = false;
							seguro = true;
						} else {
							System.out.println("Escriba una opción válida");
						}
					} while (!afirma && !afirma);

					if (afirma) {
						ControladorExportar.exportarParada(actual, fechaInicio, fechaFin);
						System.out.println("\n Quiere realizar otra exportacion? (si/no)");
						String afirmacion = leer.next();
						if (afirmacion.equalsIgnoreCase("no"))
							seguro = true;
					}

				} while (!seguro);
				break;
			case 0:
				System.out.println("Hasta la proxima.");
				MenuInvitado();
				break;
			default:
				System.out.println("Escoja una opcion valida.");
			}
		} while (opc != 0);
		leer.close();

	}
}
