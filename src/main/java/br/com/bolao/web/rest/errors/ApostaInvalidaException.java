package br.com.bolao.web.rest.errors;

public class ApostaInvalidaException  extends CustomParameterizedException {
	
	private static final long serialVersionUID = 1L;

	public ApostaInvalidaException() {
		super("Aposta inválida. Deve ser definidos os dois placares para registrar uma aposta");
	}

}
