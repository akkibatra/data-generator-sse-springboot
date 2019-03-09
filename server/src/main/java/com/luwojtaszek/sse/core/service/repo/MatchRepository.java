package com.luwojtaszek.sse.core.service.repo;

import com.luwojtaszek.sse.core.domain.model.MatchEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface MatchRepository extends CassandraRepository<MatchEntity, String> {

    MatchEntity findByMatchId(String matchId);
}
