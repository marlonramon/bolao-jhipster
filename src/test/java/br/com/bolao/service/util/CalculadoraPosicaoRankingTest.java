package br.com.bolao.service.util;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import br.com.bolao.service.dto.RankingDTO;

public class CalculadoraPosicaoRankingTest {

	@Test
	public void deveOrdenarRankingPorPontuacao() {
		
		List<RankingDTO> rankings;
		List<RankingDTO> rankingOrdenado;
		
		RankingDTO rank1 = new RankingDTO(1L,"marlon1","marlon1",5L,0L,0L,1L);
		RankingDTO rank2 = new RankingDTO(1L,"marlon2","marlon2",0L,0L,0L,0L);
		/*RankingDTO rank35 = new RankingDTO(1L,"marlon3.5","marlon3.5",15L,0L,1L,1L);
		RankingDTO rank3 = new RankingDTO(1L,"marlon3","marlon3",15L,0L,1L,1L);
		RankingDTO rank4 = new RankingDTO(1L,"marlon4","marlon4",30L,0L,2L,2L);
		RankingDTO rank5 = new RankingDTO(1L,"marlon5","marlon5",30L,1L,1L,0L);*/
		
		rankings = Arrays.asList(rank1,rank2 /*,rank3,rank4,rank5,rank35*/);
		
		rankingOrdenado = Arrays.asList(/*rank5,rank4,rank3,rank35,*/rank1,rank2);
		
		Collections.sort(rankings, new CalculadoraPosicaoRanking());
		
		new AtualizadorPosicaoRanking(rankings).atualizarPosicoes();
		
		for (RankingDTO rankingDTO : rankingOrdenado) {
			System.out.println(rankingDTO.getPosicao() + " - " + rankingDTO.getPontuacaoAtual());
		}
		
		assertArrayEquals(rankings.toArray(), rankingOrdenado.toArray());
		
		
	}

}
