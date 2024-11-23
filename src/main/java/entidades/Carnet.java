package entidades;

import java.time.LocalDate;
import java.util.Objects;

public class Carnet {
	private Long id;
	private LocalDate fechaExp = LocalDate.now();
	private double distancia = 0.00;
	private Parada paradaInicial;
	private int nVips = 0;

	public Carnet() {

	}

	public Carnet(Long id, Parada paradaInicial) {
		super();
		this.id = id;
		this.paradaInicial = paradaInicial;
	}

	public Carnet(Long id, LocalDate fechaExp, double distancia, Parada paradaInicial, int nVips) {
		super();
		this.id = id;
		this.fechaExp = fechaExp;
		this.distancia = distancia;
		this.paradaInicial = paradaInicial;
		this.nVips = nVips;
	}

	public Carnet(Long id, LocalDate fechaExp, double distancia, int nVips) {
		super();
		this.id = id;
		this.fechaExp = fechaExp;
		this.distancia = distancia;
		this.nVips = nVips;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaExp() {
		return fechaExp;
	}

	public void setFechaExp(LocalDate fechaExp) {
		this.fechaExp = fechaExp;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public int getnVips() {
		return nVips;
	}

	public void setnVips(int nVips) {
		this.nVips = nVips;
	}

	public Parada getParadaInicial() {
		return paradaInicial;
	}

	public void setParadaInicial(Parada paradaInicial) {
		this.paradaInicial = paradaInicial;
	}

	@Override
	public int hashCode() {
		return Objects.hash(distancia, fechaExp, id, nVips, paradaInicial);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carnet other = (Carnet) obj;
		return Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
				&& Objects.equals(fechaExp, other.fechaExp) && Objects.equals(id, other.id) && nVips == other.nVips
				&& Objects.equals(paradaInicial, other.paradaInicial);
	}

	@Override
	public String toString() {
		return "Carnet [id=" + id + ", fechaExp=" + fechaExp + ", distancia=" + distancia + ", paradaInicial="
				+ paradaInicial + ", nVips=" + nVips + "]";
	}

}
