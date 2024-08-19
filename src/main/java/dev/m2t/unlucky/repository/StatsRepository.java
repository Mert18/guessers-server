package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.Stats;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StatsRepository extends JpaRepository<Stats, Long> {

    Stats findFirstByOrderByLastUpdatedDesc();
}
