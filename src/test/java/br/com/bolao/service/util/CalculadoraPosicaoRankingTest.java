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
		
		RankingDTO rank1 = new RankingDTO(1L,"marlon1","marlon1",95L,2L,3L,5L);
		RankingDTO rank2 = new RankingDTO(1L,"marlon2","marlon2",95L,3L,2L,3L);
		RankingDTO rank35 = new RankingDTO(1L,"marlon3.5","marlon3.5",95L,3L,3L,1L);
		RankingDTO rank3 = new RankingDTO(1L,"marlon3","marlon3",100L,2L,4L,4L);
		RankingDTO rank4 = new RankingDTO(1L,"marlon4","marlon4",105L,3L,3L,3L);
		RankingDTO rank5 = new RankingDTO(1L,"fabiano","fabiano",115L,2L,5L,5L);
		
		
		RankingDTO rank6 = new RankingDTO(1L,"silvana","silvana",95L,2L,3L,5L);
		RankingDTO rank7 = new RankingDTO(1L,"daniel","daniel",90L,2L,4L,2L);
		RankingDTO rank8 = new RankingDTO(1L,"carlos","carlos",90L,2L,3L,4L);
		RankingDTO rank9 = new RankingDTO(1L,"mateus","mateus",90L,2L,3L,4L);
		RankingDTO rank10 = new RankingDTO(1L,"dread","dread",90L,1L,4L,6L);
		
		
		
		rankings = Arrays.asList(rank1,rank2,rank3,rank4,rank5,rank35, rank6, rank7, rank8, rank9, rank10);
		
		rankingOrdenado = Arrays.asList(rank5,rank4,rank3,rank35,rank2,rank1,rank6,rank7,rank8,rank9,rank10);
		
		Collections.sort(rankings, new CalculadoraPosicaoRanking());
		
		new AtualizadorPosicaoRanking(rankings).atualizarPosicoes();
		
		assertArrayEquals(rankings.toArray(), rankingOrdenado.toArray());
		
		
	}

}
