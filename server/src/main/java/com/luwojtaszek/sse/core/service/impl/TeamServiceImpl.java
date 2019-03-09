package com.luwojtaszek.sse.core.service.impl;

import com.luwojtaszek.sse.core.domain.model.*;
import com.luwojtaszek.sse.core.service.TeamService;
import com.luwojtaszek.sse.core.service.repo.MatchRepository;
import com.luwojtaszek.sse.core.service.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lukasz on 15.08.2017.
 * With IntelliJ IDEA 15
 */
@Service
public class TeamServiceImpl implements TeamService {

        @Autowired
        MatchRepository matchRepository;

        @Autowired
        TeamRepository teamRepository;


        List<Team> teamList = new ArrayList<>();
        MatchEntity matchEntity = new MatchEntity();
        boolean flag = false;
        boolean innings = false;
        //List<String> stockNames = Arrays.asList("mango,banana,guava,infinity".split(","));

        @Override
        public Flux<List<Team>> shuffleOffer(String matchId, final int periodInSeconds) {
            Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
            matchEntity = matchRepository.findByMatchId(matchId);
            List<TeamEntity> teamEntities = matchEntity.getTeams().stream().map(team -> teamRepository.findByTeamId(team))
                    .collect(Collectors.toList());
            teamEntities.stream().forEach(teamEntity -> {
                Team team = new Team();
                team.setName(teamEntity.getTeamId());
                team.setPlayers(new ArrayList<>());
                teamEntity.getPlayers().stream().forEach(s -> {
                    Players players = new Players();
                    team.getPlayers().add(s);
                });
                teamList.add(team);
            });
            if(matchEntity.getSportsName().equals("Horse Race")){
                updateHorseData();
                interval.subscribe((i) -> updateHorseData());
            }else {
                updateCricketData();
                interval.subscribe((i) -> updateCricketData());
            }
            Flux<List<Team>> teamsFlux = Flux.fromStream(Stream.generate(() -> teamList));
            return Flux.zip(interval, teamsFlux).map(Tuple2::getT2);
        }

        private void updateCricketData() {
            if (flag) {
                List<Team> teams = teamList.stream()
                        .filter(t -> t.getWicket() != Integer.parseInt(matchEntity.getConfig().get("wicket")))
                        .collect(Collectors.toList());
                if (teams.size() == 2 && teams.size() == 0) {
                    teams = teamList.stream().sorted((o1, o2) -> o1.getScore() - o2.getScore()).collect(Collectors.toList());
                }
                matchEntity.setWinner(teams.get(0).getName());
                matchRepository.save(matchEntity);
                teamList = null;
            }
            if (teamList != null) {
                Team team;
                if (innings) {
                    team = teamList.get(1);
                } else {
                    team = teamList.get(0);
                }
                Random rand = new Random();
                team.setScore(team.getScore() + rand.nextInt(Integer.parseInt(matchEntity.getConfig().get("run"))) + 1);
                team.setRate(rand.nextInt(500) / 100);
                team.setBallCount(team.getBallCount() + 1);
                team.setMaxBalls(Integer.parseInt(matchEntity.getConfig().get("no_of_balls")));
                int wicket = rand.nextInt(10) % 6 == 0 ? 1 : 0;
                team.setWicket(team.getWicket() + wicket);
                if (team.getWicket() == Integer.parseInt(matchEntity.getConfig().get("wicket"))
                        || team.getBallCount() == team.getMaxBalls()) {
                    if(innings)
                        flag = true;
                    else
                        innings = true;
                }
            }
        }

        private void updateHorseData() {
            if(flag) teamList = null;
            if(teamList != null) {
                teamList.stream().forEach(team -> {
                    Random rand = new Random();
                    team.setRate(rand.nextInt(500) / 100);
                    team.setDistance(team.getDistance() + (double) rand.nextInt(20) + 1);
                    if (team.getDistance() >= Double.parseDouble(matchEntity.getConfig().get("distance"))) {
                        flag = true;
                        matchEntity.setWinner(team.getName());
                        matchRepository.save(matchEntity);
                    }
                });
            }
        }
}
