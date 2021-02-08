package com.kike.colegio.entities;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;





@Entity
@Table(name = "notas")
public class NotaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@MapsId("id")
	@JoinColumn(name = "id_alumnos")
	private AlumnoEntity alumnos;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@MapsId("id")
	@JoinColumn(name = "id_asignaturas")
	private AsignaturasEntity asignaturas;

	@Column(name = "nota")
	private Double nota;	
	
	@Column(name = "fecha")
	private String fecha;
	
	
	//Constructores, Getters y setters 	

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
	 * @return the alumnos
	 */
	public AlumnoEntity getAlumnos() {
		return alumnos;
	}

	/**
	 * @param alumnos the alumnos to set
	 */
	public void setAlumnos(AlumnoEntity alumnos) {
		this.alumnos = alumnos;
	}

	/**
	 * @return the asignaturas
	 */
	public AsignaturasEntity getAsignaturas() {
		return asignaturas;
	}

	/**
	 * @param asignaturas the asignaturas to set
	 */
	public void setAsignaturas(AsignaturasEntity asignaturas) {
		this.asignaturas = asignaturas;
	}

	/**
	 * @return the nota
	 */
	public Double getNota() {
		return nota;
	}

	/**
	 * @param nota the nota to set
	 */
	public void setNota(Double nota) {
		this.nota = nota;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	/**
	 * @param id
	 * @param alumnos
	 * @param asignaturas
	 * @param nota
	 * @param fecha
	 */
	public NotaEntity(int id, AlumnoEntity alumnos, AsignaturasEntity asignaturas, Double nota, String fecha) {
		super();
		this.id = id;
		this.alumnos = alumnos;
		this.asignaturas = asignaturas;
		this.nota = nota;
		this.fecha = fecha;
	}

	
	
	/**
	 * 
	 */
	public NotaEntity() {
		super();
	}

	/**
	 * @param alumnos
	 * @param asignaturas
	 * @param nota
	 * @param fecha
	 */
	public NotaEntity(AlumnoEntity alumnos, AsignaturasEntity asignaturas, Double nota, String fecha) {
		super();
		this.alumnos = alumnos;
		this.asignaturas = asignaturas;
		this.nota = nota;
		this.fecha = fecha;
	}
}
	
	
	
	
	
	