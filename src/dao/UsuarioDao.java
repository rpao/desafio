package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import enuns.Erros;
import exception.BusinessException;
import model.Usuario;
import util.HibernateUtil;

public class UsuarioDao extends DaoBasico {
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listar() throws BusinessException {
		Session session = null;
		List<Usuario> usuarios = null;
		try {
			session = HibernateUtil.getSession();
			usuarios = (ArrayList<Usuario>) session.createCriteria(Usuario.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		} catch (final HibernateException e) {
			throw new BusinessException(Erros.BD_ERRO.getDescricao(), e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return usuarios;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Usuario> listarUsuario(Usuario filtro) throws BusinessException {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			final Criteria criteria = session.createCriteria(Usuario.class);

			if (filtro != null) {
				if (filtro.getCod() != 0)
					criteria.add(Restrictions.eq("cod", filtro.getCod()));
			}

			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			final ArrayList<Usuario> usuario = (ArrayList<Usuario>) criteria.list();

			session.close();
			return usuario;
		} catch (final Exception e) {
			throw e;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public Usuario buscarUsuario(Usuario usuario) throws BusinessException {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			final Usuario result = (Usuario) session.createCriteria(Usuario.class)
					.add(Restrictions.eq("email", usuario.getEmail()))
					.add(Restrictions.eq("senha", usuario.getSenha()))
					.uniqueResult();

			session.close();
			return result;
		} catch (final Exception e) {
			throw e;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public Usuario buscarUsuarioPorLogin(Usuario usuario) throws BusinessException {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			final Usuario result = (Usuario) session.createCriteria(Usuario.class)
					.add(Restrictions.eq("email", usuario.getEmail()))
					.uniqueResult();

			session.close();
			return result;
		} catch (final Exception e) {
			throw e;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
