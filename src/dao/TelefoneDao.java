package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import enuns.Erros;
import exception.BusinessException;
import model.Telefone;
import model.Usuario;
import util.HibernateUtil;

public class TelefoneDao extends DaoBasico{	
	@SuppressWarnings("unchecked")
	public List<Telefone> listarPorUsuario(Usuario usuario) throws BusinessException {
		Session session = null;
		List<Telefone> telefones = null;
		try {
			session = HibernateUtil.getSession();
			telefones = (ArrayList<Telefone>) session.createCriteria(Telefone.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.add(Restrictions.eq("usuario.cod", usuario.getCod()))
					.list();
		} catch (final HibernateException e) {
			throw new BusinessException(Erros.BD_ERRO.getDescricao(), e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return telefones;
	}
	
	@SuppressWarnings("unchecked")
	public void excluirPorUsuario(Usuario usuario) throws BusinessException {
		Session session = null;
		Transaction t = null;
		try {
			session = HibernateUtil.getSession();
			t = session.beginTransaction();
			
			String query = "DELETE FROM Telefone WHERE usuario.cod = " + usuario.getCod();
			Query delete = session.createQuery(query);
			delete.executeUpdate();
			t.commit();
			session.close();
		} catch (HibernateException e){
			if (t != null)
				t.rollback();
			throw new BusinessException(e.getMessage());
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
