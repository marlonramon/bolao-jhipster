package br.com.bolao.repository;

import br.com.bolao.domain.Bolao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Bolao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BolaoRepository extends JpaRepository<Bolao, Long> {
    @Query("select distinct bolao from Bolao bolao left join fetch bolao.usersBolaos")
    List<Bolao> findAllWithEagerRelationships();

    @Query("select bolao from Bolao bolao left join fetch bolao.usersBolaos where bolao.id =:id")
    Bolao findOneWithEagerRelationships(@Param("id") Long id);

}
