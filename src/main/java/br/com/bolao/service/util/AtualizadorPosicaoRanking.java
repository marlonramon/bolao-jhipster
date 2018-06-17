package br.com.bolao.service.util;

import java.util.Collection;

import br.com.bolao.service.dto.RankingDTO;

public class AtualizadorPosicaoRanking {
	
	private Collection<RankingDTO> rankings;

	public AtualizadorPosicaoRanking(Collection<RankingDTO> rankings) {
		this.rankings = rankings;
	}
	
	public void atualizarPosicoes() {
		this.rankings.stream().forEach(rank -> definirPosicao(rank, rankings));
	}
	
	private void definirPosicao(RankingDTO rank, Collection<RankingDTO> rankings) {
		
		CalculadoraPosicaoRanking calculadoraPosicaoRanking = new CalculadoraPosicaoRanking();
		
		for (RankingDTO rankingDTO : rankings) {
			
			if (!rank.equals(rankingDTO)) {
				int compararRankings = calculadoraPosicaoRanking.compare(rank, rankingDTO);
				
				if (compararRankings > 0) {
					rank.setPosicao(rankingDTO.getPosicao() + 1);					
				} else if(compararRankings < 0) {					
					
					if(rank.getPosicao() > rankingDTO.getPosicao()) {
   					   rank.setPosicao(rankingDTO.getPosicao() - 1);
					} else {
						rankingDTO.descerPosicao();
					}
					
				} else if(compararRankings == 0 ) {
					rank.setPosicao(rankingDTO.getPosicao());
				}
				
			}
			
			
		}
		
		
		
	}

}
