package controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.Contacto;

/**
 * Controlador principal para la aplicacion de gestion de contactos Maneja la
 * logica de la interfaz de usuario y las interacciones con los contactos
 */
public class PrincipalController {

	private static ObservableList<Contacto> listaContactos = FXCollections.observableArrayList();
	private static ObservableList<Contacto> favoritos = FXCollections.observableArrayList();
	private static String ruta;

	// TITULO APP
	@FXML
	private Label titAgenda;

	// BOTONES
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnConfirmar;
	@FXML
	private Button btnCancelarEdit;

	// PANELES
	@FXML
	private GridPane panelAddContactos;
	@FXML
	private GridPane panelEditarContactos;
	@FXML
	private VBox panelContactos;
	@FXML
	private VBox panelFavoritos;
	// CONTROLES DE AÑADIR CONTACTO
	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfTelefono;
	@FXML
	private TextField tfEmail;
	@FXML
	private RadioButton radioHombre;
	@FXML
	private RadioButton radioMujer;
	@FXML
	private CheckBox ckFavorito;
	@FXML
	private ChoiceBox<String> choiceGrupo;
	@FXML
	private TextArea taAdicional;
	@FXML
	private DatePicker dateFechaNac;
	@FXML
	private ImageView imgViewImagen;
	@FXML
	private ToggleGroup generos;

	// CONTROLES DE EDITAR CONTACTO
	@FXML
	private TextField tfNombreEdit;
	@FXML
	private TextField tfTelefonoEdit;
	@FXML
	private TextField tfEmailEdit;
	@FXML
	private RadioButton radioHombreEdit;
	@FXML
	private RadioButton radioMujerEdit;
	@FXML
	private CheckBox ckFavoritoEdit;
	@FXML
	private ChoiceBox<String> choiceGrupoEdit;
	@FXML
	private TextArea taAdicionalEdit;
	@FXML
	private DatePicker dateFechaNacEdit;
	@FXML
	private ImageView imgViewImagenEdit;
	@FXML
	private Image imagen;
	@FXML
	private ToggleGroup generosEdit;
	// LISTAS
	@FXML
	private ListView<Contacto> lstContactos;
	@FXML
	private ListView<Contacto> listFavoritos;

	// Metodos de inicio
	/**
	 * Inicializa los elementos de la interfaz y establece valores predeterminados
	 */
	@FXML
	public void initialize() {
		Tooltip TooltipGuardar = new Tooltip("Guardar contacto");
		Tooltip TooltipBorrar = new Tooltip("Borrar datos");
		Tooltip TooltipEditar = new Tooltip("Confirmar edicion del contacto");
		Tooltip TooltipCancelar = new Tooltip("Cancelar Edicion");
		btnConfirmar.setTooltip(TooltipEditar);
		btnGuardar.setTooltip(TooltipGuardar);
		btnCancelar.setTooltip(TooltipBorrar);
		btnCancelarEdit.setTooltip(TooltipCancelar);

		panelAddContactos.setVisible(false);
		panelEditarContactos.setVisible(false);
		panelContactos.setVisible(false);
		panelFavoritos.setVisible(false);
		lstContactos.setVisible(false);

		// Inicializamos el ChoiceBox con opciones, el equivalente al ComboBox
		choiceGrupo.setItems(FXCollections.observableArrayList("Amigos", "Familia", "Trabajo"));
		choiceGrupoEdit.setItems(FXCollections.observableArrayList("Amigos", "Familia", "Trabajo"));
		// Opcional: Establecemos un valor por defecto
		choiceGrupo.setValue("Grupo");

		tfTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				tfTelefono.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		tfTelefonoEdit.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				tfTelefonoEdit.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		handleChoice();
	}

	@FXML
	private void handleChoice() {
		choiceGrupo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				String valor = t1;

			}
		});
		choiceGrupoEdit.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ov, String t, String t1) {
				String valor = t1;

			}
		});
	}

	// METODOS PARA CAMBIAR ENTRE PESTAÑAS

	/**
	 * Muestra el panel de añadir contacto
	 */
	@FXML
	private void AddContactos() {
		panelAddContactos.setVisible(true);
		panelEditarContactos.setVisible(false);
		panelContactos.setVisible(false);
		panelFavoritos.setVisible(false);
		lstContactos.setVisible(false);
		titAgenda.setVisible(false);
	}

	/**
	 * Muestra el ppanel con la lista de contactos para seleccionar uno para su
	 * edicion
	 */
	@FXML
	private void EditContactos() {
		panelAddContactos.setVisible(false);
		panelEditarContactos.setVisible(false);
		panelContactos.setVisible(true);
		panelFavoritos.setVisible(false);
		lstContactos.setVisible(true);
		titAgenda.setVisible(false);
		ShowListaContactos();
	}

	/**
	 * Muestra el panel de edicion de contactos despues de seleccionarlo en la lista
	 */
	@FXML
	private void OpenEdicion() {
		panelAddContactos.setVisible(false);
		panelEditarContactos.setVisible(true);
		panelContactos.setVisible(false);
		panelFavoritos.setVisible(false);
		lstContactos.setVisible(false);
		titAgenda.setVisible(false);
	}

	/**
	 * Muestra la lista de contactos guardados
	 */
	@FXML
	private void ShowContactos() {
		panelAddContactos.setVisible(false);
		panelEditarContactos.setVisible(false);
		panelContactos.setVisible(true);
		lstContactos.setVisible(true);
		ShowListaContactos();
		panelFavoritos.setVisible(false);
		titAgenda.setVisible(false);

	}

	/**
	 * Muestra la lista de contactos favoritos
	 */
	@FXML
	private void ShowFavoritos() {
		panelAddContactos.setVisible(false);
		panelEditarContactos.setVisible(false);
		panelContactos.setVisible(false);
		panelFavoritos.setVisible(true);
		titAgenda.setVisible(false);
		ShowListaFavoritos();

	}

	// METODO PARA SALIR

	/**
	 * Cierra la aplicacion despues de confirmar la salida
	 */
	@FXML
	private void Salir() {
		Dialog<ButtonType> dialogo = new Dialog<>();
		dialogo.setTitle("Salir");
		dialogo.setHeaderText("¿Seguro que desea salir?");

		ButtonType btnSi = new ButtonType("SI");
		ButtonType btnNo = new ButtonType("NO");
		dialogo.getDialogPane().getButtonTypes().addAll(btnSi, btnNo);

		// Por lo que vi response viene del metodo ShowAndWait() y nos permite acceder
		// directamente al valor del boton pulsado
		dialogo.showAndWait().ifPresent(response -> {
			if (response == btnSi) {
				System.exit(0);
			}

		});
	}

	// METODOS COMPARTIDOS ENTRE PESTAÑAS

	/**
	 * Muestra la lista de contactos en la interfaz
	 */
	@FXML
	private void ShowListaContactos() {
		lstContactos.setItems(listaContactos);
		lstContactos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				UpdateCampos(newValue);
			}
		});

		lstContactos.setOnMouseClicked(event -> {
			Contacto seleccionContacto = lstContactos.getSelectionModel().getSelectedItem();
			if (seleccionContacto != null) {
				UpdateCampos(seleccionContacto);
				OpenEdicion();
			}
		});

		lstContactos.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				Contacto seleccionContacto = lstContactos.getSelectionModel().getSelectedItem();
				if (seleccionContacto != null) {
					UpdateCampos(seleccionContacto);
					OpenEdicion();
				}
			}
		});
	}

	/**
	 * Muestra la lista de contactos favoritos en la interfaz
	 */
	@FXML
	private void ShowListaFavoritos() {
		listFavoritos.setItems(favoritos);
		listFavoritos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				UpdateCampos(newValue);
			}
		});
	}

	/**
	 * Verifica si los campos necesarios para un contacto estan vacios
	 * 
	 * @return true si algun campo esta vacio, false si todos los campos estan
	 *         completos
	 */
	@FXML
	private boolean CamposVacios() {
		if (tfNombre.getText().isBlank() || tfTelefono.getText().isBlank() || tfEmail.getText().isBlank()
				|| choiceGrupo.getValue() == null || dateFechaNac.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Datos vacíos");
			alert.setHeaderText(
					"Alguno de los siguientes campos está vacío: Nombre, Teléfono, Email, Grupo, Fecha de nacimiento");
			alert.setContentText("Por favor, rellene los campos indicados arriba.");

			alert.showAndWait();

			return true;
		}
		return false;
	}

	/**
	 * Verifica si los campos necesarios para editar un contacto estan vacios
	 * 
	 * @return true si algun campo esta vacio, false si todos los campos estan
	 *         completos
	 */
	@FXML
	private boolean CamposVaciosEdit() {
		if (tfNombreEdit.getText().isBlank() || tfTelefonoEdit.getText().isBlank() || tfEmailEdit.getText().isBlank()
				|| choiceGrupoEdit.getValue() == null || dateFechaNacEdit.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Datos vacíos");
			alert.setHeaderText(
					"Alguno de los siguientes campos está vacío: Nombre, Teléfono, Email, Grupo, Fecha de nacimiento");
			alert.setContentText("Por favor, rellene los campos indicados arriba.");

			alert.showAndWait();

			return true;
		}
		return false;
	}

	/**
	 * Obtiene el genero seleccionado en el panel de añadir contacto
	 * 
	 * @return el genero seleccionado, "Hombre" o "Mujer"
	 */
	@FXML
	private String SelectedGenero() {

		if (generos.getSelectedToggle().equals(radioHombre))
			return "Hombre";
		else
			return "Mujer";
	}

	/**
	 * Obtiene el genero seleccionado en el panel de edicion de contacto
	 * 
	 * @return el genero seleccionado, "Hombre" o "Mujer"
	 */
	@FXML
	private String EditGenero() {

		if (generosEdit.getSelectedToggle().equals(radioHombreEdit))
			return "Hombre";
		else
			return "Mujer";
	}

	/**
	 * Abre un selector de archivos para elegir una imagen y la establece en los
	 * campos de imagen de los formularios de añadir y editar contacto. Si se
	 * selecciona una imagen, la ruta de la imagen se guarda y se muestra en los
	 * campos de imagen. Si no se selecciona ninguna imagen, la ruta se establece
	 * como vacia
	 */
	@FXML
	private void SelectImagen() {
		FileChooser selector = new FileChooser();
		selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg"));

		File seleccion = selector.showOpenDialog(new Stage());
		if (seleccion != null) {
			Image imgSeleccionada = new Image(seleccion.toURI().toString());
			ruta = imgSeleccionada.getUrl();
			imgViewImagen.setImage(imgSeleccionada);
			imgViewImagenEdit.setImage(imgSeleccionada);
		} else {
			ruta = "";
		}
	}

	/**
	 * Limpia los campos de los formularios de añadir o editar contacto. Este metodo
	 * borra el contenido de los campos de texto, desmarca las casillas de
	 * verificacion, borra las selecciones de los campos de fecha y grupo, y elimina
	 * las imagenes mostradas
	 */
	@FXML
	private void LimpiarPantalla() {
		tfNombre.clear();
		tfTelefono.clear();
		tfEmail.clear();
		choiceGrupo.getSelectionModel().clearSelection();
		ckFavorito.setSelected(false);
		taAdicional.clear();
		dateFechaNac.setValue(null);
		imgViewImagen.setImage(null);
	}

	// METODOS PARA ALMACENAR CONTACTOS

	/**
	 * Guarda un nuevo contacto en la lista
	 */
	@FXML
	private void SaveContacto() {
		if (!CamposVacios()) {
			Contacto nuevoContacto = new Contacto();

			nuevoContacto.setNombre(tfNombre.getText());
			nuevoContacto.setTelefono(tfTelefono.getText());
			nuevoContacto.setEmail(tfEmail.getText());
			nuevoContacto.setGenero(SelectedGenero());

			if (ckFavorito.isSelected())
				nuevoContacto.setFavorito(true);
			else
				nuevoContacto.setFavorito(false);

			nuevoContacto.setGrupo(choiceGrupo.getValue());
			nuevoContacto.setNotasAdicionales(taAdicional.getText());
			nuevoContacto.setFechaNac(dateFechaNac.getValue());

			if (ruta != null) {
				nuevoContacto.setImagen(ruta);
			} else {
				nuevoContacto.setImagen("No hay imagen especificada");
			}

			listaContactos.add(nuevoContacto);

			if (nuevoContacto.isFavorito()) {
				favoritos.add(nuevoContacto);
			}

			Dialog<String> dialogo = new Dialog<>();
			dialogo.setTitle("Contacto guardado");
			dialogo.setHeaderText("Contacto guardado exitosamente");

			ButtonType btnLeido = new ButtonType("Leído");
			dialogo.getDialogPane().getButtonTypes().add(btnLeido);

			dialogo.showAndWait();
			LimpiarPantalla();
		}
	}

	// METODOS PARA EDITAR CONTACTOS

	/**
	 * Actualiza los campos del formulario de edicion con los datos del contacto
	 * seleccionado
	 * 
	 * @param contacto El contacto a editar
	 */
	public void UpdateCampos(Contacto contacto) {
		tfNombreEdit.setText(contacto.getNombre());
		tfTelefonoEdit.setText(contacto.getTelefono());
		tfEmailEdit.setText(contacto.getEmail());

		if (contacto.getGenero().equals("Hombre")) {
			radioHombreEdit.setSelected(true);
			radioMujerEdit.setSelected(false);
		} else if (contacto.getGenero().equals("Mujer")) {
			radioHombreEdit.setSelected(true);
			radioMujerEdit.setSelected(false);
		}

		if (choiceGrupoEdit.getItems().contains(contacto.getGrupo())) {
			choiceGrupoEdit.setValue(contacto.getGrupo());
		} else {
			choiceGrupoEdit.setValue(contacto.getGrupo());

			if (contacto.isFavorito())
				ckFavoritoEdit.setSelected(true);
			else
				ckFavoritoEdit.setSelected(false);
			taAdicionalEdit.setText(contacto.getNotasAdicionales());

			dateFechaNacEdit.setValue(contacto.getFechaNac());

			try {
				imagen = new Image(new FileInputStream(contacto.getImagen()));
				imgViewImagenEdit.setImage(imagen);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Realiza la edicion del contacto seleccionado
	 */
	@FXML
	public void EditingContactos() {

		Contacto contactoElegido = lstContactos.getSelectionModel().getSelectedItem();

		if (contactoElegido == null) {
			Dialog<String> dialogo = new Dialog<>();
			dialogo.setTitle("Tienes que seleccionar un contacto");
			dialogo.setHeaderText("Por favor selecciona primero un contacto");
			ButtonType btnLeido = new ButtonType("Leído");
			dialogo.getDialogPane().getButtonTypes().add(btnLeido);
			dialogo.showAndWait();
			return;
		}

		if (CamposVaciosEdit()) {
			return;
		}

		contactoElegido.setNombre(tfNombreEdit.getText());
		contactoElegido.setTelefono(tfTelefonoEdit.getText());
		contactoElegido.setEmail(tfEmailEdit.getText());
		contactoElegido.setGenero(EditGenero());
		contactoElegido.setFavorito(ckFavoritoEdit.isSelected());
		contactoElegido.setGrupo(choiceGrupoEdit.getValue());
		contactoElegido.setNotasAdicionales(taAdicionalEdit.getText());
		contactoElegido
				.setFechaNac(dateFechaNacEdit.getValue() != null ? dateFechaNacEdit.getValue() : LocalDate.now());

		if (ruta != null && !ruta.isEmpty()) {
			contactoElegido.setImagen(ruta);
		}

		if (contactoElegido.isFavorito()) {
			if (!favoritos.contains(contactoElegido)) {
				favoritos.add(contactoElegido);
			}
		} else {
			favoritos.remove(contactoElegido);
		}

		UpdateCampos(contactoElegido);

		lstContactos.refresh();

		Dialog<String> dialogo = new Dialog<>();
		dialogo.setTitle("Contacto editado");
		dialogo.setHeaderText("Contacto editado exitosamente");
		ButtonType btnLeido = new ButtonType("Leído");
		dialogo.getDialogPane().getButtonTypes().add(btnLeido);
		dialogo.showAndWait();
	}

	/**
	 * Cancela la edicion de un contacto y muestra la lista de contactos
	 */
	@FXML
	private void Cancelar() {
		ShowContactos();
	}

	// METODO PARA EXPORTAR CONTACTOS EN UN XML

	/**
	 * Exporta la lista de contactos a un archivo XML
	 */
	@FXML
	public void ExportContactos() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			DOMImplementation implementacion = db.getDOMImplementation();

			Document documento = implementacion.createDocument(null, "contactos", null);
			Element contactos = documento.getDocumentElement();

			documento.setXmlVersion("1.0");
			ProcessingInstruction ip = documento.createProcessingInstruction("xml-stylesheet",
					"type=\"text/xml\" href=\"test.xsl\"");
			documento.insertBefore(ip, contactos);

			for (Contacto contactoAñadir : listaContactos) {

				Element contacto = documento.createElement("contacto");
				contactos.appendChild(contacto);

				Element nombre = documento.createElement("nombre");
				nombre.appendChild(documento.createTextNode(contactoAñadir.getNombre()));
				contacto.appendChild(nombre);

				Element telefono = documento.createElement("telefono");
				telefono.appendChild(documento.createTextNode(contactoAñadir.getTelefono()));
				contacto.appendChild(telefono);

				Element email = documento.createElement("email");
				email.appendChild(documento.createTextNode(contactoAñadir.getEmail()));
				contacto.appendChild(email);

				Element genero = documento.createElement("genero");
				genero.appendChild(documento.createTextNode(contactoAñadir.getGenero()));
				contacto.appendChild(genero);

				Element favorito = documento.createElement("favorito");
				favorito.appendChild(documento.createTextNode(contactoAñadir.isFavorito() ? "Si" : "No"));
				contacto.appendChild(favorito);

				Element grupo = documento.createElement("grupo");
				grupo.appendChild(documento.createTextNode(contactoAñadir.getGrupo()));
				contacto.appendChild(grupo);

				Element notasAdicionales = documento.createElement("notasAdicionales");
				notasAdicionales.appendChild(documento.createTextNode(
						contactoAñadir.getNotasAdicionales() != null ? contactoAñadir.getNotasAdicionales() : ""));
				contacto.appendChild(notasAdicionales);

				Element fechaNac = documento.createElement("fechaNac");
				fechaNac.appendChild(documento.createTextNode(contactoAñadir.getFechaNac().toString()));
				contacto.appendChild(fechaNac);

				Element imagen = documento.createElement("imagen");
				imagen.appendChild(
						documento.createTextNode(contactoAñadir.getImagen() != null ? contactoAñadir.getImagen()
								: "No hay imagen especificada"));
				contacto.appendChild(imagen);
			}

			TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
			Transformer transformador = fabricaTransformador.newTransformer();
			Source fuente = new DOMSource(documento);
			Result resultado = new StreamResult(new File(".\\archivos\\contactos.xml"));
			transformador.transform(fuente, resultado);

		} catch (ParserConfigurationException ex) {
			System.out.println("Error: " + ex.getMessage());
		} catch (TransformerConfigurationException ex) {
			System.out.println("Error: " + ex.getMessage());
		} catch (TransformerException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		Dialog<String> dialogo = new Dialog<>();
		dialogo.setTitle("Contacto exportado");
		dialogo.setHeaderText("Contacto exportado exitosamente");

		ButtonType btnLeido = new ButtonType("Leido");
		dialogo.getDialogPane().getButtonTypes().add(btnLeido);

		dialogo.showAndWait();

	}
}
