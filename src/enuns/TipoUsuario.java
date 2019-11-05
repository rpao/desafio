package enuns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum TipoUsuario implements Serializable {

	CLIENTE("Cliente", 0), GESTOR("Gestor", 1), SUPERVISOR("Supervisor",2);

	private String descricao;
	private int codigo;

	public String getDescricao() {
		return descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	private TipoUsuario(String descricao, int codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public static TipoUsuario porCodigo(int codigo) {
		for (TipoUsuario tipo : TipoUsuario.values()) {
			if (codigo == tipo.getCodigo())
				return tipo;
		}

		throw new IllegalArgumentException("Código Inválido");
	}

	public static List<TipoUsuario> getList() {
		List<TipoUsuario> list = new ArrayList<TipoUsuario>();

		for (TipoUsuario tc : TipoUsuario.values()) {
			list.add(tc);
		}

		return list;
	}

	public static int numero(TipoUsuario tipo) {
		switch (tipo) {
		case CLIENTE:
			return 0;
		case GESTOR:
			return 1;
		default:
			return -1;
		}
	}
}