package Vista;

import java.util.InputMismatchException;
import java.util.Scanner;

import entidades.Perfil;



public class Menus {
	
	public Menus() {

	}

	public void MenuInvitado() {
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
				do {
					System.out.println("--Login--");
					System.out.println("Introduzca su nombre de usuario:");
					String nombreUsuario = leer.next();
					System.out.println("Introduzca su contraseña:");
					String password = leer.next();

					actual = admin.loginAdmin(nombreUsuario, password);
					if (actual != null)

						MenuAdmin();
					else {

						actual = gestor.comprobarLogin(nombreUsuario, password);

						if (actual != null) {
							if (actual.getPerfil().equals(Perfil.PEREGRINO))
								MenuPeregrino();
							else if (actual.getPerfil().equals(Perfil.PARADA))
								MenuResponsable();

						} else {
							System.out.println("Usuario y/o contraseña incorrectos");
						}
					}
				} while (actual == null);

				break;
			case 2:
				String nombreUsuarioP = "";
				String passwordP = "";
				int nacionalidad = 0;
				do {
					System.out.println("--Registro--");
					System.out.println("Introduzca nombre de usuario:"
							+ "tenga en cuenta que un nombre compuesto no debe tener espacios");

					boolean valido = false;
					do {
						nombreUsuarioP = leer.nextLine();
						Perfil p = gestor.ExisteUsuario(nombreUsuarioP);
						if (p != null) {
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
					String nombreNacion = "";
					do {
						try {
							System.out.println("Introduce la nacionalidad del peregrino:");
							peControlador.MostrarNaciones();
							nacionalidad = leer.nextInt();

							nombreNacion = peControlador.ComprobarNacion(nacionalidad);
							if (nombreNacion == null) {
								System.out.println("La nacionalidad introducida no es valida, introduce otra:");
								peControlador.MostrarNaciones();
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
					do {
						try {
							System.out.println("Introduce el id de la parada en la que estas:");
							pControlador.MostrarParadas();
							paradaIni = leer.nextLong();
							valido = true;
						} catch (InputMismatchException e) {
							System.out.println("Solo se permiten numeros enteros");
							leer.nextLine();
						}
					} while (!valido);
					peregrino = peControlador.RegistrarPeregrino(nombreUsuarioP, passwordP, nombreNacion, paradaIni);

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

	public void MenuAdmin() {
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
					existe = pControlador.ExisteParada(nombreParada, region);
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
						Perfil perfil = gestor.ExisteUsuario(nombreResponsable);
						if (perfil != null) {
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
					registrado = pControlador.RegistrarResponsable(nombreResponsable, password);
					if (!registrado) {
						System.out.println("Error ya existe ese nombre de usuario");
					}
				} while (!registrado);

				pControlador.RegistrarParada(nombreParada, region, nombreResponsable);
				break;
			case 0:
				System.out.println("Hasta la proxima.");
				MenuInvitado();
				break;
			default:
				System.out.println("Escoja una opcion valida.");

			}
		} while (opc != 0);

	}

	public void MenuPeregrino() {
		int opc = -1;
		Scanner leer = new Scanner(System.in);
		do {
			System.out.println("--Menu Peregrino--");
			System.out.println("1. Exportar Carnet(Funcion aun no disponible)");
			System.out.println("0. Logout");
			opc = leer.nextInt();

			switch (opc) {
			case 1:
				System.out.println("Esta seria la opcion de exportar carnet");
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

	public void MenuResponsable() {
		int opc = -1;
		Scanner leer = new Scanner(System.in);
		do {
			System.out.println("--Menu Responsable--");
			System.out.println("1. Sellar Carnet(Funcion aun no disponible)");
			System.out.println("0. Logout");
			opc = leer.nextInt();

			switch (opc) {
			case 1:
				System.out.println("Esta seria la opcion de sellar carnet");
				break;
			case 0:
				System.out.println("Hasta la proxima.");
				MenuInvitado();
				break;
			default:
				System.out.println("Escoja una opcion valida.");
			}
		} while (opc != 0);
		leer.close()
	}
}
