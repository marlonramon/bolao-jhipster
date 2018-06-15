package br.com.bolao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bolao.domain.Bolao;
import br.com.bolao.domain.Campeonato;
import br.com.bolao.domain.User;
import br.com.bolao.service.dto.RankingDTO;


@Repository
public interface BolaoRepository extends JpaRepository<Bolao, Long> {
	
    @Query("select distinct bolao from Bolao bolao left join fetch bolao.usersBolaos")
    List<Bolao> findAllWithEagerRelationships();

    @Query("select bolao from Bolao bolao left join fetch bolao.usersBolaos where bolao.id =:id")
    Bolao findOneWithEagerRelationships(@Param("id") Long id);
    
    
    @Query("select distinct bolao from Bolao bolao join bolao.usersBolaos users join fetch bolao.campeonato where users.login =:login")
    List<Bolao> findAllByUserLogin(@Param("login") String login);
    
    @Query("select distinct bolao from Bolao bolao join bolao.usersBolaos users join fetch bolao.campeonato campeonato where campeonato =:campeonato and users =:user")
    Bolao findByCampeonatoAndUser(@Param("campeonato") Campeonato campeonato, @Param("user") User user);
    
    
    @Query(" select new br.com.bolao.service.dto.RankingDTO(bolao.id, "
    		+ "	            user.login, "
    		+ "	            user.firstName, "
    		+ "             sum(apostas.pontuacao) as pontuacaoAtual,  "
			+ "        sum(CASE "
			+ "           when apostas.pontuacao = bolao.pontosAcertoDoisPlacares then 1 "
			+ "           else 0"
			+ "        end) as aposta_certeira,"
			+ "        sum(CASE "
			+ "           when (apostas.pontuacao = bolao.pontosAcertoResultado or apostas.pontuacao = (bolao.pontosAcertoResultado + bolao.pontosAcertoUmPlacar) ) then 1 "
			+ "           else 0"
			+ "        end) as aposta_resultado,"
			+ "        sum(CASE "
			+ "           when (apostas.pontuacao = bolao.pontosAcertoUmPlacar or apostas.pontuacao = (bolao.pontosAcertoResultado + bolao.pontosAcertoUmPlacar) ) then 1 "
			+ "           else 0"
			+ "        end) as aposta_um_placar)"  
    		+ " from Bolao bolao "    		
    		+ " join bolao.campeonato campeonato "
    		+ " join campeonato.rodadas rodadas "
    		+ " join rodadas.partidas   partidas "
    		+ " join partidas.apostas   apostas "
    		+ " join apostas.user       user "
    		+ "  where bolao.id =:idBolao "    		
    		+ " group by bolao.id,  "
    		+ "          user.login"
    		+ " order by pontuacaoAtual desc,"
    		+ "          aposta_certeira desc,"
    		+ "          aposta_resultado desc,"
    		+ "          aposta_um_placar desc,"
    		+ "          user.firstName")
    List<RankingDTO> findByRankingFromBolao(@Param("idBolao")Long idBolao);
    
    
    
    

}
