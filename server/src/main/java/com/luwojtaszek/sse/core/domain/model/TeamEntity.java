package com.luwojtaszek.sse.core.domain.model;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Data
@Table("teams")
public class TeamEntity {

    @PrimaryKeyColumn(name="team_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String teamId;
    private List<String> players;
}
