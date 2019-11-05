package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.event.RowEditEvent;

import exception.BusinessException;
import model.Telefone;
import model.Usuario;
import service.TelefoneService;
import service.UsuarioService;
import util.MensagemUsuario;

@SuppressWarnings("serial")
@ManagedBean(name = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {
	private String msgInserirTelefone;
	private String msgConfirmaSenha;
	private String confirmaSenha;
	private Usuario usuario;
	private Telefone telefone;
	private List<Usuario> listaUsuarios;
	private List<Telefone> listaTelefoneUsuario;
	private UsuarioService usuarioService;
	private TelefoneService telefoneService;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Telefone> getListaTelefoneUsuario() {
		return listaTelefoneUsuario;
	}

	public void setListaTelefoneUsuario(List<Telefone> listaTelefoneUsuario) {
		this.listaTelefoneUsuario = listaTelefoneUsuario;
	}

	public String getMsgInserirTelefone() {
		return msgInserirTelefone;
	}

	public void setMsgInserirTelefone(String msgInserirTelefone) {
		this.msgInserirTelefone = msgInserirTelefone;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
	public String getMsgConfirmaSenha() {
		return msgConfirmaSenha;
	}

	public void setMsgConfirmaSenha(String msgConfirmaSenha) {
		this.msgConfirmaSenha = msgConfirmaSenha;
	}

	@PostConstruct
	public void init() {
		this.msgInserirTelefone = "";
		this.msgConfirmaSenha = "";
		this.confirmaSenha = "";
		this.usuario = new Usuario();
		this.telefone = new Telefone();
		this.usuarioService = new UsuarioService();
		this.telefoneService = new TelefoneService();
		this.listaUsuarios = listarUsuarios();
		this.listaTelefoneUsuario = new ArrayList<Telefone>();
	}
	
	public void limparFormulario(){
		this.msgInserirTelefone = "";
		this.msgConfirmaSenha = "";
		this.confirmaSenha = "";
		this.usuario = new Usuario();
		this.telefone = new Telefone();
		this.listaUsuarios = listarUsuarios();
		this.listaTelefoneUsuario = new ArrayList<Telefone>();
	}
	
	public void atualizarListagem(){
		this.listaUsuarios = listarUsuarios();
	}
	
	public List<Usuario> listarUsuarios(){
		List<Usuario> lista = new ArrayList<Usuario>();
		try {
			lista = this.usuarioService.listar();
		} catch (BusinessException e) {
			MensagemUsuario.notificarErro("Não foi possível carregar os dados.");
			e.printStackTrace();
		}  catch (Exception e) {
			MensagemUsuario.notificarErro("Ocorreu um erro inesperado no sistema. Por favor, contacte o administrador.");
			e.printStackTrace();
		}		
		return lista;
	}
	
	public void listarTelefoneUsuario(){
		this.listaTelefoneUsuario = new ArrayList<Telefone>();
		try {
			this.listaTelefoneUsuario  = this.telefoneService.listarPorUsuario(this.usuario);
		} catch (BusinessException e) {
			MensagemUsuario.notificarErro("Não foi possível carregar os dados.");
			e.printStackTrace();
		}  catch (Exception e) {
			MensagemUsuario.notificarErro("Ocorreu um erro inesperado no sistema. Por favor, contacte o administrador.");
			e.printStackTrace();
		}
	}
	
	public void deletarUsuario(){
		try {
			this.telefoneService.excluirPorUsuario(this.usuario);
			this.usuarioService.excluir(this.usuario);
			this.limparFormulario();
			MensagemUsuario.notificarSucesso("Usuário excluído com sucesso.");
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
			MensagemUsuario.notificarErro("Erro ao excluir usuário.<br>Tente novamente mais tarde ou contacte o administrador.");
		}
	}
	
	public void salvarUsuario(){
		this.msgConfirmaSenha = "";
		
		if (this.usuario.getCod() != null && this.confirmaSenha.isEmpty()){
			this.confirmaSenha = "" + this.usuario.getSenha(); 
		}
		
		if (this.usuario.getSenha().length() < 6){
			this.msgConfirmaSenha = "A senha deve ter no mínimo 6 caracteres.";
		} else if (this.usuario.getCod() == null && !this.usuario.getSenha().equals(this.confirmaSenha)){
			this.msgConfirmaSenha = "Confirmação de senha incorreta.";
		} else {
			try {
				Usuario usuarioSalvo = this.usuarioService.salvar(this.usuario);
				if (this.usuario.getCod() == null){
					for (Telefone tel : this.listaTelefoneUsuario){
						tel.setUsuario(usuarioSalvo);
						this.telefoneService.inserirAlterar(tel);
					}
				}
				this.limparFormulario();
				MensagemUsuario.notificarSucesso("Usuário salvo com sucesso.");
			} catch (BusinessException e) {
				System.out.println(e.getMessage());
				MensagemUsuario.notificarErro("Erro ao salvar usuário.<br>Tente novamente mais tarde ou contacte o administrador.");
			}
		}
	}
 
    public void adicionarTelefone() {
    	boolean save = true; 
    	this.msgInserirTelefone = "";
    	// verifica campos preenchidos
    	if (this.telefone.getNumero().isEmpty()){
    		this.msgInserirTelefone += "Número não informado.";
    		save = false;
    	}
    	
    	if (this.telefone.getTipo().isEmpty()){
    		this.msgInserirTelefone += "Tipo de Telefone não informado.";
    		save = false;
    	}
    	
    	if (save){
    		// se tem código -> edição
	    	if (this.usuario.getCod() != null){
	    		// salva telefone no banco
		    	try{
		    		this.telefone.setUsuario(this.usuario);
		    		this.telefoneService.inserirAlterar(this.telefone);
		    		this.telefone = new Telefone();
			        this.listarTelefoneUsuario();
		    	} catch (BusinessException e){
		    		System.out.println(e.getMessage());
		    		this.msgInserirTelefone = "Erro ao inserir telefone.<br>Tente novamente ou contacte o administrador.";
		    	}
	    	}else{
	    		// mantém telefone na lista para salvar posteriormente
	    		Telefone novoTelefone = new Telefone();
	    		novoTelefone.setDdd(this.telefone.getDdd());
	    		novoTelefone.setNumero(this.telefone.getNumero());
	    		novoTelefone.setTipo(this.telefone.getTipo());
	    		novoTelefone.setUsuario(this.usuario);	    		
	    		this.listaTelefoneUsuario.add(novoTelefone);
	    		
	    		this.telefone = new Telefone();
	    	}
    	}
    }
    
    public void removerTelefone() {
    	// se tem código, é edição
    	if(this.usuario.getCod() != null){
	    	try{
	    		// exclui direto do banco
		    	this.telefone.setUsuario(this.usuario);
		        this.telefoneService.excluir(this.telefone);
		        this.telefone = new Telefone();
		        this.listarTelefoneUsuario();
	    	} catch (BusinessException e){
	    		System.out.println(e.getMessage());
	    		this.msgInserirTelefone = "Erro ao excluir registro.<br>Tente novamente ou contacte o administrador.";
	    	}
    	}else{
    		// exclui da lista
    		boolean removed = this.listaTelefoneUsuario.remove(this.telefone);
    		if (!removed){
    			this.msgInserirTelefone = "Erro ao excluir registro.<br>Tente novamente ou contacte o administrador.";
    		}
    	}
    }
}
