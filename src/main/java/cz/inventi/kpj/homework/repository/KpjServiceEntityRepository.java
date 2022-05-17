package cz.inventi.kpj.homework.repository;

import cz.inventi.kpj.homework.entity.KpjServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KpjServiceEntityRepository extends JpaRepository<KpjServiceEntity, Long> {

    @Query
    Optional<KpjServiceEntity> findByName(String name);
}
