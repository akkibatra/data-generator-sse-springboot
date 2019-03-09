package com.makeathon.sse.core.service.repo;

import com.makeathon.sse.core.domain.model.TeamEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface TeamRepository extends CassandraRepository<TeamEntity, String> {

    TeamEntity findByTeamId(String matchId);
}
