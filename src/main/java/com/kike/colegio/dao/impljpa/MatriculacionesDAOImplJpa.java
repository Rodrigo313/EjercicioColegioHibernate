/**
 * 
 */
package com.kike.colegio.dao.impljpa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.kike.colegio.dao.MatriculacionDAO;
import com.kike.colegio.dtos.MatriculacionDTO;
import com.kike.colegio.entities.AlumnoEntity;
import com.kike.colegio.entities.AsignaturasEntity;
import com.kike.colegio.entities.CajaEntity;
import com.kike.colegio.entities.MatriculacionesEntity;
import com.kike.colegio.utils.DBUtils;



/**
 * @author Giddy
 *
 */
public class MatriculacionesDAOImplJpa implements MatriculacionDAO{

	@Override
	public List<MatriculacionDTO> obtenerMatriculacionesPorIdasigNombreAsigIdalumNombrealumFechaActivo(String idAsig, String nombreAsig, String idAlum, String nombreAlum, String fecha, String activo) {
		
		String jpql = " select new com.kike.colegio.dtos.MatriculacionDTO (m.id, asig.id, asig.nombre, a.id, a.nombre, m.fecha, m.activo)"
				+ " FROM MatriculacionesEntity as m "
				+ " INNER JOIN AlumnoEntity as a on m.idAlumno.id = a.id"
				+ " INNER JOIN AsignaturasEntity as asig on m.idAsignatura.id = asig.id"
				+ " where asig.nombre LIKE :asignombre and CAST(a.id as string) like :idalumno "
				+ " and m.fecha LIKE :fecha and CAST(m.activo as string) LIKE :activo";
		
		EntityManagerFactory emf =  DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		javax.persistence.Query query = em.createQuery(jpql).setParameter("asignombre", "%" + nombreAsig + "%").setParameter("idalumno", "%" + idAlum + "%").setParameter("fecha", "%" + fecha + "%").setParameter("activo", activo);
		List<MatriculacionDTO> listarMatriculacion = query.getResultList();
		
		em.close();
		
		return listarMatriculacion;
		
	}
	
	@Override
	public List<MatriculacionDTO> obtenerMatriculacionesPorNombreAsigNombrealumFecha(String asignatura,  String nombre, String fecha) {
		String jpql = " select new com.kike.colegio.dtos.MatriculacionDTO (m.id, asig.id, asig.nombre, a.id, a.nombre, m.fecha, m.activo)"
				+ " FROM MatriculacionesEntity as m "
				+ " INNER JOIN AlumnoEntity as a on m.idAlumno.id = a.id"
				+ " INNER JOIN AsignaturasEntity as asig on m.idAsignatura.id = asig.id"
				+ " where asig.nombre LIKE :asignombre and a.nombre like :alumnoNombre and m.fecha LIKE :fecha";
				
		EntityManagerFactory emf =  DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		javax.persistence.Query query = em.createQuery(jpql).setParameter("alumnoNombre", "%" + nombre + "%").setParameter("asignombre", "%" + asignatura + "%").setParameter("fecha", "%" + fecha + "%");
		List<MatriculacionDTO> listarMatriculacion = query.getResultList();
		
		em.close();
		
		return listarMatriculacion;
		
		
	}
	

	@Override
	public Integer insertarMatriculacion(String idAsignatura, String idAlumno, String tasa, String fecha, String activo) {
		Date cDate = new Date();
		String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
		
		if(fecha == "") {
			 fecha = fDate;
		}
		
		EntityManagerFactory emf =  DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		AsignaturasEntity asignatura = em.find(AsignaturasEntity.class, Integer.parseInt(idAsignatura));
		AlumnoEntity alumno = em.find(AlumnoEntity.class, Integer.parseInt(idAlumno));
		
		MatriculacionesEntity a = new MatriculacionesEntity(asignatura, alumno, fecha, Integer.parseInt(activo));
		
		CajaEntity c = new CajaEntity(a, Double.parseDouble(tasa));
		
		em.persist(a);
		em.getTransaction();
		
		em.persist(c);
		
		em.getTransaction().commit();
		
		em.close();
		
		
		return (Integer) emf.getPersistenceUnitUtil().getIdentifier(c);
	}

	@Override
	public Integer obetenerAsignaturasAlumno(String idAlumno) {
		String jpql = " select count(m.idAsignatura)"
				+ " FROM MatriculacionesEntity as m"
				+ " where CAST(m.idAlumno.id as string) LIKE :idAlumno";
				
		EntityManagerFactory emf =  DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		javax.persistence.Query query = em.createQuery(jpql).setParameter("idAlumno", "%" + idAlumno + "%");
		int resultado = (int) query.setMaxResults(1).getFirstResult();
		
		return resultado;
	}

	@Override
	public Integer borrarMatriculacion(String idMatricula) {
		EntityManagerFactory emf =  DBUtils.creadorEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		CajaEntity c = em.find(CajaEntity.class, Integer.parseInt(idMatricula));
		
		if(c != null) {
			em.remove(c);
			em.getTransaction();
			
			MatriculacionesEntity a = em.find(MatriculacionesEntity.class, Integer.parseInt(idMatricula));
			
			if(a != null) {
				em.remove(a);
				em.getTransaction().commit();
				em.close();
				
				return 1;
			}
		}
		
		em.close();
		
		return 0;
	}

	@Override
	public Integer obtenerNombreAsignaturaFecha(String nombre, String asignatura, String fecha) {
		String sql = "select m.id_alumno, a.nombre, asig.nombre, m.fecha from matriculaciones as m "
				+ "inner join alumnos as a on m.id_alumno = a.id "
				+ "inner join asignaturas as asig on m.id_asignatura = asig.id where a.nombre = ? and asig.nombre = ? and m.fecha = ? ;";
		Connection connection = DBUtils.DBConnection();
		
		PreparedStatement ps;
		int resultado = 0;
		try {
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, "%" + nombre + "%");
			ps.setString(2, "%" + asignatura + "%");
			ps.setString(3, "%" + fecha + "%");
			
			
			resultado = ps.executeUpdate();
			
			
			ps.close();
			
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		
		return resultado;
	}

	

}
