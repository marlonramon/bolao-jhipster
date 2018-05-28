package br.com.bolao.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ApostaDuplicadaException extends AbstractThrowableProblem {

	private static final long serialVersionUID = 1L;
	
	public ApostaDuplicadaException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.BAD_REQUEST);
    }
	

}
