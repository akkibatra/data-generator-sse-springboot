package com.luwojtaszek.sse.web.controller;

import com.luwojtaszek.sse.core.domain.model.Offer;
import com.luwojtaszek.sse.core.domain.model.Team;
import com.luwojtaszek.sse.core.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Created by lukasz on 15.08.2017.
 * With IntelliJ IDEA 15
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/match/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<Team>> highlightedOffer(@PathVariable String id) {
        return teamService.shuffleOffer(id, 3);
    }

}
