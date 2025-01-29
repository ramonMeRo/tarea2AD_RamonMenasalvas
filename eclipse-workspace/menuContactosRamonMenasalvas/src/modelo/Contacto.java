package modelo;

import java.time.LocalDate;

public class Contacto {

	private String nombre;
	private String telefono;
	private String email;
	private String genero;
	private boolean favorito = false;
	private String grupo;
	private String notasAdicionales;
	private LocalDate fechaNac;
	private String imagen;

	public Contacto() {
	}

	public Contacto(String nombre, String telefono, String email, String genero, boolean favorito, String grupo,
			String notasAdicionales, LocalDate fechaNac, String imagen) {
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.genero = genero;
		this.favorito = favorito;
		this.grupo = grupo;
		this.notasAdicionales = notasAdicionales;
		this.fechaNac = fechaNac;
		this.imagen = imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public boolean isFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getNotasAdicionales() {
		return notasAdicionales;
	}

	public void setNotasAdicionales(String notasAdicionales) {
		this.notasAdicionales = notasAdicionales;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "Contacto Nombre=" + nombre + " || Telefono=" + telefono + " || Email=" + email + " || Genero=" + genero
				+ " || Favorito=" + favorito + " || Grupo=" + grupo + " || NotasAdicionales=" + notasAdicionales
				+ " || FechaNac=" + fechaNac + " || Imagen=" + imagen + "]";
	}

}
