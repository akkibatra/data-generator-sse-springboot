package com.makeathon.sse.core.domain.model;

import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Table("matches")
public class MatchEntity {

    @PrimaryKeyColumn(name="match_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String matchId;
    @Column("sports_name")
    private String sportsName;
    @Column("match_name")
    private String matchName;
    @Column("start_time")
    private Date startTime;
    @Column("end_time")
    private Date endTime;
    private List<String> teams;
    private Map<String, String> config;
    private String winner;
}
