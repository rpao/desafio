package enuns;

public enum Erros {
	/**
	 * Erro referente ao erro de restrição no Banco de Dados
	 */
	BD_RESTRICAO(0, "Esse registro possui outros registros associados. Exclusão não permitida."),

	/**
	 * Erro referente a conexão com o Banco de Dados.
	 */
	BD_ERRO(1, "Banco de Dados OFFLINE"),

	/**
	 * Erro genérico ou desconhecido
	 */
	BD_GENERIC_ERROR(2, "Alguma coisa de errado aconteceu no Banco de Dados"),

	/**
	 * Erro de restrição exclusiva: primary key está sento utilizada por um registro
	 */
	BD_RESTRICAO_EXCLUSIVA_VIOLADA(3, "Restrição exclusiva violada"),
	/**
	 * Parâmetro obrigatório não preenchido
	 */
	PARAMETRO_NULL(4, "Parâmetro obrigatório não preenchido"),

	/**
	 * Erro de restrição exclusiva: registro já existe
	 */
	DUPLICIDADE_ERRO(5, "Registro existente"),

	/**
	 * Parametro inválido
	 */
	PARAMETRO_INVALIDO(6, "Parâmetro inválido"),

	/**
	 * Existe apenas um usuário registrado
	 */
	APENAS_UM_USUARIO(7, "Existe apenas um usuário registrado"),

	/**
	 * Restrição de referencia: há registros associados
	 */
	REGISTRO_POSSUI_VINCULOS(8, "Exclusão não pode ser finalizada porque há registros associados."),

	/**
	 * CPF não encontrao
	 */
	RELACIONAMENTO_PESSOA_VEICULO_INVALIDO(12, "O CPF informado não foi encontrado"),

	/**
	 * CNP não encontrado
	 */
	RELACIONAMENTO_EMPRESA_VEICULO_INVALIDO(13, "O CNPJ informado não foi encontrado"),

	/**
	 * Erro genérico ou desconhecido
	 */
	GENERIC_ERROR(14, "Erro"),

	/**
	 * CNPJ inválido 
	 */
	CNPJ_INVALIDO(15,"O CNPJ informado é inválido"),

	/**
	 * Registro já cadastrado.
	 */
	KEY_VIOLATION(16, "Registro já cadastrado no sistema. Inclusão não permitida."),

	/**
	 * Registro não existe
	 */
	REGISTRO_NOT_FOUND(17, "Registro não encontrado. A solicitação não poderar ser finalizada."),

	/**
	 * porte de veículo com intervalo de eixo inválido
	 */
	RANGE_OUT_BOUND(18, "Intervalo de Eixos inválido.<br>Não pode haver interseção entre intervalos de diferentes portes de veículos.<br>"
			+ "Inclusão não permitida."),

	/**
	 * porte de veículo com intervalo de eixo inválido
	 */
	INVALID_FINAL_RANGE(18, "Intervalo inválido: Valor inicial deve ser maior que o valor final.<br>Inclusão não permitida."),

	/**
	 * CPF inválido
	 */
	INVALID_CPF(19,"O CPF informado não é válido."),

	/**
	 * CPF ou Passaporte já cadastrado no sistema
	 */
	CPF_UNIQUE_VALUE_VIOLATION(20,"O CPF informado já está cadastrado no sistema."),

	/**
	 * CPF ou Passaporte já cadastrado no sistema
	 */
	PASSPORT_UNIQUE_VALUE_VIOLATION(21,"O Passaporte informado já está cadastrado no sistema."),

	/**
	 * CPF ou Passaporte deve ser preenchido.
	 */
	CPF_PASSPORT_REQUIRED(22,"O CPF ou o Passaporte deve ser preenchido."),

	/**
	 * NOME deve ser preenchido.
	 */
	NAME_REQUIRED(23,"O Nome deve ser preenchido."),

	/**
	 * Erro genérico de inclusão
	 */
	GENERIC_REGISTER_ERROR(24, "Erro ao cadastrar registro."),

	/**
	 * Erro genérico de edição
	 */
	GENERIC_EDITION_ERROR(24, "Erro ao editar registro."),

	/**
	 * Erro genérico de exclusão;
	 */
	GENERIC_EXCLUSION_ERROR(26, "Erro ao excluir registro.");

	private final int codigo;
	private final String descricao;

	private Erros(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		return this.codigo + ": " + this.descricao;
	}
}
