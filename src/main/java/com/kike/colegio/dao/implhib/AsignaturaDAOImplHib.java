package com.kike.colegio.dao.implhib;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


import com.kike.colegio.dao.AsignaturaDAO;
import com.kike.colegio.dtos.AlumnoDTO;
import com.kike.colegio.dtos.AsignaturaDTO;
import com.kike.colegio.entities.AsignaturasEntity;
import com.kike.colegio.utils.DBUtils;



public class AsignaturaDAOImplHib implements AsignaturaDAO {


@Override
public List<AsignaturaDTO> obtenerAsignaturaPorIdNombreCursoTasa(String id, String nombre, String curso, String tasa) {
	String jpql = " select new com.kike.colegio.dtos.AsignaturaDTO (a.id, a.nombreAsignatura, a.curso, a.tasa)"
			+ "FROM AsignaturasEntity a";
	
//	public AsignaturaDTO(int id, String nombre, int curso, double tasa)
	
	SessionFactory factory = DBUtils.creadorSessionFactory();
	Session s = factory.getCurrentSession();
	s.beginTransaction();
	
	Query query = s.createQuery(jpql);
	List<AsignaturaDTO> lista = query.getResultList();

	s.close(); // Cerramos la sesión

	return lista;
	
}

@Override
public Integer insertarAsignatura(String id, String nombre, String curso, String tasa) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Integer actualizarAsignatura(String idOld, String idNew, String nombre, String curso, String tasa) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Integer eliminarAsignatura(String id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int obtenerNumeroAsignaturasMatriculadas(String idAlumno) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public double obtenerTasaAsignatura(String idAsignatura) {
	// TODO Auto-generated method stub
	return 0;
}

}