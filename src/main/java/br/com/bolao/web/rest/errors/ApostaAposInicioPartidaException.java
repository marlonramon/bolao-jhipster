package br.com.bolao.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ApostaAposInicioPartidaException extends AbstractThrowableProblem {

	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_MESSAGE = "Não é permitido registrar uma aposta após o inicio da partida";
	
	public ApostaAposInicioPartidaException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.BAD_REQUEST);
    }
	
	public ApostaAposInicioPartidaException() {
        this(DEFAULT_MESSAGE);
    }

}
