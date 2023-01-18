package app.foot.controller;

import app.foot.model.Match;
import app.foot.model.PlayerScorer;
import app.foot.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class MatchController {
    private final MatchService service;

    @GetMapping("/matches")
    public List<Match> getMatches() {
        return service.getMatches();
    }

    @PostMapping("/matches/{matchId}/goals")
    public List<Match> postGoal(
            @PathVariable String matchId,
            @RequestBody List<PlayerScorer> playerScorers
            ){
        return service.addGoals(matchId, playerScorers);
    }
}
