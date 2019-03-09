package com.makeathon.sse.core.service.repo;

import com.makeathon.sse.core.domain.model.MatchEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface MatchRepository extends CassandraRepository<MatchEntity, String> {

    MatchEntity findByMatchId(String matchId);
}
