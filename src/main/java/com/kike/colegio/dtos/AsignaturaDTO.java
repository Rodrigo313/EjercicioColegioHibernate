package com.kike.colegio.dtos;

public class AsignaturaDTO {
	
	private int id;
	private String nombre;
	private int curso;
	private double tasa;
	
	/**
	 * 
	 */
	public AsignaturaDTO() {
		super();
	}
	/**
	 * @param id
	 * @param nombre
	 * @param curso
	 * @param tasa
	 */
	public AsignaturaDTO(int id, String nombre, int curso, double tasa) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.curso = curso;
		this.tasa = tasa;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the curso
	 */
	public int getCurso() {
		return curso;
	}
	/**
	 * @param curso the curso to set
	 */
	public void setCurso(int curso) {
		this.curso = curso;
	}
	/**
	 * @return the tasa
	 */
	public double getTasa() {
		return tasa;
	}
	/**
	 * @param tasa the tasa to set
	 */
	public void setTasa(double tasa) {
		this.tasa = tasa;
	}
	
	
	
	
	
	

}
