package enuns;

public enum StatusUsuario {
	ATIVO("Ativo", 0), BLOQUEADO("Bloqueado", 1);

	private String descricao;
	private int codigo;

	public String getDescricao() {
		return descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	private StatusUsuario(String descricao, int codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}
}
