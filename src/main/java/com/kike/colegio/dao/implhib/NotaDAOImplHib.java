package com.kike.colegio.dao.implhib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


import com.kike.colegio.dao.NotaDAO;
import com.kike.colegio.dtos.NotaDTO;
import com.kike.colegio.entities.AlumnoEntity;
import com.kike.colegio.entities.AsignaturasEntity;
import com.kike.colegio.entities.NotaEntity;
import com.kike.colegio.utils.DBUtils;

public class NotaDAOImplHib implements NotaDAO {

	@Override
	public List<NotaDTO> obtenerNotaPorIdNombreAsignaturaNotaFecha(String idAlumno, String nombre, String asignatura,
			String nota, String fecha) {
		String jpql = "select new com.kike.colegio.dto.NotaDTO (n.id, a.id, a.nombre, asi.id, asi.nombre, n.nota, n.fecha"
				+ "FROM NotaEntity n JOIN AlumnoEntity a ON n.alumnos.id = a.id "
				+ "JOIN AsignaturaEntity asi ON n.asignaturas.id = asi.id "
				+ "WHERE CAST(a.id AS string) LIKE :idAlumno AND a.nombre LIKE :nombre AND asi.nombre LIKE :asignatura "
				+ "AND CAST(n.nota AS string) LIKE :nota AND n.fecha LIKE :fecha";
		
		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();
		
		Query query = s.createQuery(jpql);
		query.setParameter("idAlumno", "%" + idAlumno + "%");
		query.setParameter("nombre", "%" + nombre + "%");
		query.setParameter("asignatura", "%" + asignatura + "%");
		query.setParameter("nota", "%" + nota + "%");
		
		if(fecha == "") {
			Date fecha1 = new Date();
			String fecha2 = new SimpleDateFormat("yyyy-MM-dd").format(fecha1);
			query.setParameter("fecha", "%" + fecha1 + "%");
		}else {
			query.setParameter("fecha", "%" + fecha + "%");
		}
		
		List<NotaDTO> lista = query.getResultList();
		
		return lista;
	}

	@Override
	public List<NotaDTO> obtenerNotaPorNombreAsignaturaFecha(String nombre, String asignatura, String fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insertarNota(String idAlumno, String idAsignatura, String nota, String fecha) {
		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();
		
		AlumnoEntity a = s.find(AlumnoEntity.class, Integer.parseInt(idAlumno));
		AsignaturasEntity asi = s.find(AsignaturasEntity.class, Integer.parseInt(idAsignatura));
		
		Integer idA = a.getId();
		
		NotaEntity n = new NotaEntity(a, asi, Double.parseDouble(nota), fecha);
		
		Integer idPk = (Integer) s.save(n);
		
		s.getTransaction().commit();
		
		return idPk;
	}

	@Override
	public Integer actualizarNota(String idNota, String idAlumno, String idAsignatura, String nota, String fecha) {
		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();
		
		AlumnoEntity a = s.find(AlumnoEntity.class, Integer.parseInt(idAlumno));
		AsignaturasEntity asi = s.find(AsignaturasEntity.class, Integer.parseInt(idAsignatura));
		
		NotaEntity n = new NotaEntity(Integer.parseInt(idNota), a, asi, Double.parseDouble(nota), fecha);
		
		s.update(n);
		s.getTransaction().commit();
		
		return a.getId();
		
	}

	@Override
	public Integer eliminarNota(String id) {
		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();
		
		NotaEntity n = s.get(NotaEntity.class, Integer.parseInt(id));
		
		if(n != null) {
			s.delete(n);
			s.getTransaction().commit();
			s.close();
			
			return 1;
		}
		s.close();
		return 0;
	}


}