package entidades;

import java.util.Objects;

public class Usuario {
	private long id;
	private String nombre;
	private String password;
	private Perfil perfil = Perfil.INVITADO;

	public Usuario() {
	}

	public Usuario(long id, String nombre, String password, Perfil perfil) {
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.perfil = perfil;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, password, perfil);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return id == other.id && Objects.equals(nombre, other.nombre) && Objects.equals(password, other.password)
				&& perfil == other.perfil;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", password=" + password + ", perfil=" + perfil + "]";
	}

}
