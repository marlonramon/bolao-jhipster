package br.com.bolao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bolao.domain.Bolao;
import br.com.bolao.domain.Campeonato;
import br.com.bolao.domain.User;


@Repository
public interface BolaoRepository extends JpaRepository<Bolao, Long> {
	
    @Query("select distinct bolao from Bolao bolao left join fetch bolao.usersBolaos")
    List<Bolao> findAllWithEagerRelationships();

    @Query("select bolao from Bolao bolao left join fetch bolao.usersBolaos where bolao.id =:id")
    Bolao findOneWithEagerRelationships(@Param("id") Long id);
    
    
    @Query("select distinct bolao from Bolao bolao join bolao.usersBolaos users join fetch bolao.campeonato where users.login =:login")
    List<Bolao> findAllByUserLogin(@Param("login") String login);
    
    @Query("select distinct bolao from Bolao bolao join bolao.usersBolaos users join fetch bolao.campeonato campeonato where users.login =:login and campeonato =:campeonato")
    Bolao findByCampeonatoAndUser(Campeonato campeonato, String login);
    
    
    

}
