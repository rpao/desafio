package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import exception.BusinessException;

public class MensagemUsuario {

	public static void notificarSucesso(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, ""));

		RequestContext.getCurrentInstance().execute("$('.modal-backdrop').hide();");
	}

	public static void notificarErro(BusinessException e) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));

		RequestContext.getCurrentInstance().execute("$('.modal-backdrop').hide();");
	}

	public static void notificarErro(Exception e) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));

		RequestContext.getCurrentInstance().execute("$('.modal-backdrop').hide();");
	}

	public static void notificarErro(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));

		RequestContext.getCurrentInstance().execute("$('.modal-backdrop').hide();");
	}
}