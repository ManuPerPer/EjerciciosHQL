package Tablas;
// Generated 23 ene 2023 15:52:09 by Hibernate Tools 5.4.33.Final

/**
 * MinisterioBackup generated by hbm2java
 */
public class MinisterioBackup implements java.io.Serializable {

	private Integer codMinisterio;
	private String nombre;
	private double presupuesto;
	private double gastos;

	public MinisterioBackup() {
	}

	public MinisterioBackup(String nombre, double presupuesto, double gastos) {
		this.nombre = nombre;
		this.presupuesto = presupuesto;
		this.gastos = gastos;
	}

	public Integer getCodMinisterio() {
		return this.codMinisterio;
	}

	public void setCodMinisterio(Integer codMinisterio) {
		this.codMinisterio = codMinisterio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPresupuesto() {
		return this.presupuesto;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public double getGastos() {
		return this.gastos;
	}

	public void setGastos(double gastos) {
		this.gastos = gastos;
	}

}
