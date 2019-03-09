package com.luwojtaszek.sse.core.domain.model;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("players")
public class PlayerEntity {

    @PrimaryKeyColumn(name="player_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String playerId;
    private String playerName;
    private Integer ranking;
}
