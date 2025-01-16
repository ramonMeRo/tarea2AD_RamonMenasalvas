package entidades;

public class Sesion {
	private Perfil perfil = Perfil.INVITADO;
	private String nombre;
	private Long id;
	
	public Sesion() {
	}

	public Sesion(Perfil perfil, String nombre, Long id) {
		this.perfil = perfil;
		this.nombre = nombre;
		this.id = id;
	}

	// GETTERS Y SETTERS
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Sesion [perfil=" + perfil + ", nombre=" + nombre + ", id=" + id + "]";
	}
	

}
