/**
 * 
 */
package com.kike.colegio.dao.impljpa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.kike.colegio.dao.NotaDAO;
import com.kike.colegio.dtos.NotaDTO;
import com.kike.colegio.entities.AlumnoEntity;
import com.kike.colegio.entities.AsignaturasEntity;
import com.kike.colegio.entities.NotaEntity;
import com.kike.colegio.utils.DBUtils;




/**
 * @author Giddy
 *
 */
public class NotaDAOImplJpa implements NotaDAO {

	@Override
	public List<NotaDTO> obtenerNotaPorIdNombreAsignaturaNotaFecha(String id, String nombre, String asignatura, String nota, String fecha) {
		String jpql = " select new com.kike.colegio.dtos.NotaDTO (n.id, a.id, a.nombre, asig.id, asig.nombre, n.nota, n.fecha)"
				+ " FROM NotaEntity as n "
				+ " INNER JOIN AlumnoEntity as a on n.idAlumno.id = a.id"
				+ " INNER JOIN AsignaturasEntity as asig on n.idAsignatura.id = asig.id"
				+ " where CAST(n.id as string) LIKE :id and a.nombre like :nombre and asig.nombre LIKE :asignatura"
				+ " and CAST(n.nota as string) LIKE :nota and CAST(n.fecha as string) between :fecha and current_date()";
				
		EntityManagerFactory emf = DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		javax.persistence.Query query = em.createQuery(jpql).setParameter("id", "%" + id + "%").setParameter("nombre", "%" + nombre + "%").setParameter("asignatura", "%" + asignatura + "%").setParameter("nota", "%" + nota + "%").setParameter("fecha", fecha);
		List<NotaDTO> lista = query.getResultList();
		
		em.close();
		
		return lista;
		
		
	}

	@Override
	public List<NotaDTO> obtenerNotaPorNombreAsignaturaFecha(String nombre, String asignatura, String fecha) {
		String jpql = " select new com.kike.colegio.dtos.NotaDTO (n.id, a.id, a.nombre, asig.id, asig.nombre, n.nota, n.fecha)"
				+ " FROM NotaEntity as n "
				+ " INNER JOIN AlumnoEntity as a on n.idAlumno.id = a.id"
				+ " INNER JOIN AsignaturasEntity as asig on n.idAsignatura.id = asig.id"
				+ " where CAST(a.nombre as string) like :nombre and CAST(asig.nombre as string) LIKE :asignatura"
				+ " and CAST(n.fecha as string) between :fecha and current_date()";
		
		EntityManagerFactory emf = DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		javax.persistence.Query query = em.createQuery(jpql).setParameter("nombre", "%" + nombre + "%").setParameter("asignatura", "%" + asignatura + "%").setParameter("fecha", fecha);
		List<NotaDTO> lista = query.getResultList();
		
		em.close();
		
		return lista;
	}

	@Override
	public Integer insertarNota(String alumno, String asignatura, String nota, String fecha) {
		Date cDate = new Date();
		String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
		
		if(fecha == "") {
			 fecha = fDate;
		}
		
		EntityManagerFactory emf = DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		
		AlumnoEntity idAlumno = em.find(AlumnoEntity.class, Integer.parseInt(alumno));
		AsignaturasEntity idAsignatura = em.find(AsignaturasEntity.class, Integer.parseInt(asignatura));
		
		NotaEntity n = new NotaEntity(idAlumno, idAsignatura, Double.parseDouble(nota), fecha);
		
		em.persist(n);
		
		em.getTransaction().commit();
		
		em.close();
		
		return (Integer) emf.getPersistenceUnitUtil().getIdentifier(n);
		
	}

	@Override
	public Integer actualizarNota(String idAlumno, String idAsignatura, String nota, String fecha,
			String idAlumnosAntiguo) {
		EntityManagerFactory emf = DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		AlumnoEntity idAlumno2 = em.find(AlumnoEntity.class, Integer.parseInt(idAlumnosAntiguo));
		AsignaturasEntity idAsignatura2 = em.find(AsignaturasEntity.class, Integer.parseInt(idAsignatura));
		
		NotaEntity n = new NotaEntity(idAlumno2, idAsignatura2, Double.parseDouble(nota), fecha);
		
		n.setAlumnos(idAlumno2);
		n.setAsignaturas(idAsignatura2);
		n.setNota(Double.parseDouble(nota));
		n.setFecha(fecha);
	
		em.getTransaction().commit();
		
		em.close();
		
		return n.getId();
		
	}

	@Override
	public Integer eliminarNota(String idNota) {
		EntityManagerFactory emf = DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		NotaEntity n = em.find(NotaEntity.class, idNota);
		
		if(n != null) {
			em.remove(n);
			em.getTransaction().commit();
			
			em.close();
			
			return 1;
		}
		
		em.close();
		
		return 0;
	}

}
