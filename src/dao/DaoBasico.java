package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import exception.BusinessException;
import util.HibernateUtil;


public class DaoBasico {

	public Object inserirAlterar(Object entidade) throws BusinessException{
		Session session = null;
		Transaction t = null;
		try {
			session = HibernateUtil.getSession();
			t = session.beginTransaction();
			Object obj = session.merge(entidade);
			t.commit();
			session.close();
			return obj;
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

	public void excluir(Object entidade) throws BusinessException{
		Session session = null;
		Transaction t = null;
		try {
			session = HibernateUtil.getSession();
			t = session.beginTransaction();
			session.delete(entidade);
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