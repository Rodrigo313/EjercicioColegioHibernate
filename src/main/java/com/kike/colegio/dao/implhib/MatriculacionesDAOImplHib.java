package com.kike.colegio.dao.implhib;

import java.util.List;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.kike.colegio.dao.MatriculacionDAO;
import com.kike.colegio.dtos.MatriculacionDTO;
import com.kike.colegio.entities.AlumnoEntity;
import com.kike.colegio.utils.DBUtils;

public class MatriculacionesDAOImplHib implements MatriculacionDAO {

	@Override
	public List<MatriculacionDTO> obtenerMatriculacionesPorIdasigNombreAsigIdalumNombrealumFechaActivo(String idAsig,
			String nombreAsig, String idAlum, String nombreAlum, String fecha, String activo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insertarMatriculacion(String idAsignatura, String idAlumno, String tasa, String fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer borrarMatriculacion(String idMatricula) {
		// TODO Auto-generated method stub
		return null;
	}

}