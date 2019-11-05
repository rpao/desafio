package service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.JDBCException;
import org.hibernate.exception.JDBCConnectionException;

import dao.UsuarioDao;
import enuns.Erros;
import exception.BusinessException;
import model.Usuario;

@SuppressWarnings("serial")
public class UsuarioService implements Serializable {
	private final UsuarioDao dao;

	//private final Logger logger = Logger.getLogger(UsuarioNegocios.class);

	public UsuarioService() {
		this.dao = new UsuarioDao();
	}
	
	public List<Usuario> listar() throws BusinessException{
		return this.dao.listar();
	}
	
	public Usuario salvar(Usuario usuario) throws BusinessException{
		return (Usuario) this.dao.inserirAlterar(usuario);
	}
	
	public void excluir(Usuario usuario) throws BusinessException{
		this.dao.excluir(usuario);
	}
	
	public Usuario buscarLoginSenha(Usuario filtro) {
		Usuario resultado = null;

		try {
			resultado = this.dao.buscarUsuario(filtro);
		} catch (final BusinessException e) {
			return null;
		}

		return resultado;
	}

	public Usuario buscarLogin(Usuario filtro) {
		Usuario resultado = null;
		try {
			resultado = this.dao.buscarUsuarioPorLogin(filtro);
		} catch (final BusinessException e) {
			return null;
		}

		return resultado;
	}
}
