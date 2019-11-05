package controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;
import service.UsuarioService;
import util.MensagemUsuario;

@SuppressWarnings("serial")
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {
	private Usuario usuario;
	private Usuario usuarioLogado;
	private UsuarioService usuarioService;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioService getNegocios() {
		return usuarioService;
	}

	public void setNegocios(UsuarioService negocios) {
		this.usuarioService = negocios;
	}

	@PostConstruct
	public void init() {
		this.usuario = new Usuario();
		this.usuarioService = new UsuarioService();
	}

	public String logar() {
		Usuario usuarioCadastrado = this.usuarioService.buscarLogin(this.usuario);
		
		if (usuarioCadastrado == null){
			MensagemUsuario.notificarErro("Usuário não encontrado.");
			return "";
		}
		
		if (usuarioCadastrado.getSenha().equals(this.usuario.getSenha())){
			this.usuario.setSenha(null);
			this.usuario.setNome(usuarioCadastrado.getNome());
			this.usuario.setEmail(usuarioCadastrado.getEmail());
			MensagemUsuario.notificarSucesso("Login efetuado com sucesso");
			return "sistema/home.jsf";
		}else{
			MensagemUsuario.notificarErro("Senha Inválida.");
			return "";
		}
	}

	public void logout() {
		this.usuario = new Usuario();
		this.usuarioService = new UsuarioService();
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(ec.getRequestContextPath() + "/index.jsf?faces-redirect=true");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void redirecionarHome() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String url = request.getRequestURI();
		String[] split = url.split("/");

		try {
			response.sendRedirect("/" + split[1] + "/sistema/home.jsf");
		} catch (IOException ex) {

		}
	}
}