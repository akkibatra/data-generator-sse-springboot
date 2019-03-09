package com.luwojtaszek.sse.core.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private String name;
    private int score;
    private int wicket;
    private double distance;
    private int maxBalls;
    private int ballCount;
    private double rate;
    private List<String> players;
}
