package br.com.bolao.service.util;

import br.com.bolao.domain.Placar;
import br.com.bolao.domain.Resultado;

public class CalculadorResultadoPlacar {

		

	public Resultado calcularResultado(Placar placar) {

		if (placar.isValido()) {

			Short placarMandante = placar.getPlacarMandante();
			Short placarVisitante = placar.getPlacarVisitante();

			boolean isPlacarMandanteGreaterThenPlacarVisitante = placarMandante.compareTo(placarVisitante) > 0;
			boolean isPlacarVisitanteGreaterThenPlacarMandante = placarVisitante.compareTo(placarMandante) > 0;

			if (isPlacarMandanteGreaterThenPlacarVisitante) {
				return Resultado.MANDANTE;
			} else if (isPlacarVisitanteGreaterThenPlacarMandante) {
				return Resultado.VISITANTE;
			} else {
				return Resultado.EMPATE;
			}

		}

		return Resultado.NAODEFINIDO;
	}

}
