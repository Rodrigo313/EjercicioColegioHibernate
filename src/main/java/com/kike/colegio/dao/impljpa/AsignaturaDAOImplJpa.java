/**
 * 
 */
package com.kike.colegio.dao.impljpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.kike.colegio.dao.AsignaturaDAO;
import com.kike.colegio.dtos.AsignaturaDTO;
import com.kike.colegio.entities.AsignaturasEntity;
import com.kike.colegio.utils.DBUtils;



/**
 * @author Giddy
 *
 */
public class AsignaturaDAOImplJpa implements AsignaturaDAO{

	@Override
	public List<AsignaturaDTO> obtenerAsignaturaPorIdNombreCursoTasa(String id, String nombre, String curso , String tasa) {
		String jpql = " select new com.kike.colegio.dtos.AsignaturaDTO (asig.id, asig.nombre, asig.curso, asig.tasa)"
				+ " FROM AsignaturasEntity asig where CAST( asig.id AS string ) LIKE :id and asig.nombre like :nombre" 
				+ " and CAST( asig.curso AS string ) LIKE :curso and CAST(asig.tasa as string) LIKE :tasa";
				
			EntityManagerFactory emf =  DBUtils.creadorSessionFactory();
			EntityManager em = emf.createEntityManager();
			
			em.getTransaction().begin();
			javax.persistence.Query query = em.createQuery(jpql).setParameter("id", "%" + id + "%").setParameter("nombre", "%" + nombre + "%").setParameter("curso", "%" + curso + "%").setParameter("tasa", "%" + tasa + "%");
			List<AsignaturaDTO> lista = query.getResultList();
			em.close();

		return lista;
	}

	@Override
	public Integer insertarAsignatura(String id, String nombre, String curso, String tasa) {
		AsignaturasEntity a = new AsignaturasEntity(Integer.parseInt(id), nombre, Integer.parseInt(curso), Double.parseDouble(tasa));
		
		EntityManagerFactory emf =  DBUtils.creadorSessionFactory();
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
		
		em.close();
	
		return (Integer) emf.getPersistenceUnitUtil().getIdentifier(a);
	}

	@Override
	public Integer actualizarAsignatura(String idOld, String idNew, String nombre, String curso, String tasa) {
				
		EntityManagerFactory emf =  DBUtils.creadorSessionFactory();
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		AsignaturasEntity a = em.find(AsignaturasEntity.class, idOld);
		
		a.setId(Integer.parseInt(idNew));
		a.setNombreAsignatura(nombre);
		a.setCurso(Integer.parseInt(curso));
		a.setTasa(Double.parseDouble(tasa));
		
		em.getTransaction().commit();
		
		em.close();
		
		return a.getId();
		
	}

	@Override
	public Integer eliminarAsignatura(String id) {
		EntityManagerFactory emf =  DBUtils.creadorSessionFactory();
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		AsignaturasEntity a = em.find(AsignaturasEntity.class, id);
		
		if(a != null) {
			em.remove(a);
			em.getTransaction().commit();
			
			em.close();
			return 1;
		}
		
		em.close();
		
		return 0;
	}

	@Override
	public double obtenerTasaAsignatura(String idAsignatura) {
		String jpql = " select new com.kike.colegio.dtos.AsignaturaDTO (asig.id) FROM AsignaturasEntity where CAST(asig.id) LIKE :id";
		
		EntityManagerFactory emf = DBUtils.creadorSessionFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		javax.persistence.Query query = em.createQuery(jpql).setParameter("id", "%" + idAsignatura + "%");
		int resultado = (int)query.setMaxResults(1).getFirstResult();
		
		em.close();
		
		return resultado;
	}

	@Override
	public int obtenerNumeroAsignaturasMatriculadas(String idAlumno) {
		// TODO Auto-generated method stub
		return 0;
	}

}
