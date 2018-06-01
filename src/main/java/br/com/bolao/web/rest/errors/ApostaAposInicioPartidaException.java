package br.com.bolao.web.rest.errors;

public class ApostaAposInicioPartidaException extends CustomParameterizedException {

	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_MESSAGE = "Não é permitido registrar uma aposta após o inicio da partida";
	
	public ApostaAposInicioPartidaException(String message) {
        super(message);
    }
	
	public ApostaAposInicioPartidaException() {
        this(DEFAULT_MESSAGE);
    }

}
