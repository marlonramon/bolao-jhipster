package br.com.bolao.service.util;

import java.util.Comparator;

import br.com.bolao.service.dto.RankingDTO;

public class CalculadoraPosicaoRanking implements Comparator<RankingDTO> {
	
	@Override
	public int compare(RankingDTO rankUserOne, RankingDTO rankUserTwo) {
		
		
		Long actualPointsFirstUser = rankUserOne.getPontuacaoAtual();		
		Long actualPointsSecondUser = rankUserTwo.getPontuacaoAtual();
		
		int compare = actualPointsFirstUser.compareTo(actualPointsSecondUser);
		
		return -(compare == 0 ? compareEqualsRankingVO(rankUserOne, rankUserTwo) : compare);
		
	}
	
	private int compareEqualsRankingVO(RankingDTO rankOne, RankingDTO rankTwo) {
		
		long apostaCerteiraRankOne = rankOne.getApostaCerteira();
		long apostaCerteiraRankTwo = rankTwo.getApostaCerteira();
		
		long apostaResultadoOne = rankOne.getApostaResultado();
		long apostaResultadoTwo = rankTwo.getApostaResultado();
		
		long apostaUmPlacarOne = rankOne.getApostaUmPlacar();
		long apostaUmPlacarTwo = rankTwo.getApostaUmPlacar();
		
		if (apostaCerteiraRankOne == apostaCerteiraRankTwo && apostaResultadoOne == apostaResultadoTwo && apostaUmPlacarOne == apostaUmPlacarTwo) {
			return 0;
		}
		
		if (apostaCerteiraRankOne > apostaCerteiraRankTwo) { 
			return 1;
		} else if(apostaCerteiraRankOne == apostaCerteiraRankTwo) {
			if(apostaResultadoOne > apostaResultadoTwo){		
				return 1;			 
			} else if(apostaResultadoOne == apostaResultadoTwo) {
				if(apostaUmPlacarOne > apostaUmPlacarTwo) {
					return 1;
				}
			} 
				
		}
		
		return -1;
		
	}
	
}
