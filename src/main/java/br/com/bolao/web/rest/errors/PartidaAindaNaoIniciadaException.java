package br.com.bolao.web.rest.errors;

public class PartidaAindaNaoIniciadaException extends CustomParameterizedException{

	public PartidaAindaNaoIniciadaException() {
		super("Não é permitido consultar as apostas de uma partida ainda não iniciada"); 
	}

	private static final long serialVersionUID = 1L;
	

}
