package service;

import java.util.List;

import dao.TelefoneDao;
import exception.BusinessException;
import model.Telefone;
import model.Usuario;

public class TelefoneService {
	private TelefoneDao dao;
	
	public TelefoneService (){
		this.dao = new TelefoneDao();
	}
	
	public List<Telefone> listarPorUsuario(Usuario usuario) throws BusinessException{
		return this.dao.listarPorUsuario(usuario);
	}
	
	public Telefone inserirAlterar(Telefone telefone) throws BusinessException{
		return (Telefone) this.dao.inserirAlterar(telefone);
	}
	
	public void excluir(Telefone telefone) throws BusinessException{
		this.dao.excluir(telefone);
	}
	
	public void excluirPorUsuario(Usuario usuario) throws BusinessException{
		this.dao.excluirPorUsuario(usuario);
	}
}
