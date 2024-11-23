package entidades;

import java.time.LocalDate;
import java.util.Objects;

public class Estancia {
	private Long id;
	private LocalDate fecha;
	private boolean vip = false;
	private Peregrino peregrino;
	private Parada Parada;

	public Estancia() {

	}

	public Estancia(Long id, LocalDate fecha, boolean vip) {

		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
	}

	public Estancia(Long id, LocalDate fecha, boolean vip, Peregrino peregrino, Parada Parada) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.peregrino = peregrino;
		this.Parada = Parada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public Parada getParada() {
		return Parada;
	}

	public void setParada(Parada parada) {
		Parada = parada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Parada, fecha, id, peregrino, vip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estancia other = (Estancia) obj;
		return Objects.equals(Parada, other.Parada) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(id, other.id) && Objects.equals(peregrino, other.peregrino) && vip == other.vip;
	}

	@Override
	public String toString() {
		return "Estancia [id=" + id + ", fecha=" + fecha + ", vip=" + vip + ", peregrino=" + peregrino + ", Parada="
				+ Parada + "]";
	}
	
	public boolean esVip() {
		return vip;
	}

}
