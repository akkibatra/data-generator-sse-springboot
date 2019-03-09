package com.luwojtaszek.sse.core.service;

import com.luwojtaszek.sse.core.domain.model.Offer;
import com.luwojtaszek.sse.core.domain.model.Team;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Created by lukasz on 15.08.2017.
 * With IntelliJ IDEA 15
 */
public interface TeamService {
    Flux<List<Team>> shuffleOffer(String id, int periodInSeconds);
}
