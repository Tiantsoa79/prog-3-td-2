package app.foot.service;

import app.foot.model.Match;
import app.foot.model.PlayerScorer;
import app.foot.repository.MatchRepository;
import app.foot.repository.entity.MatchEntity;
import app.foot.repository.entity.PlayerScoreEntity;
import app.foot.repository.mapper.MatchMapper;
import app.foot.repository.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MatchService {
    private final MatchRepository repository;
    private final MatchMapper mapper;
    private final PlayerMapper playerMapper;

    public List<Match> getMatches() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    public List<Match> addGoals(String id_match, List<PlayerScorer> playerScorers) {
     MatchEntity match = repository.findById(Integer.valueOf(id_match)).get();
     List<PlayerScoreEntity> playerScoreEntities = playerScorers.stream()
             .map(playerMapper::toDomain)
             .toList();

        match.getScorers().addAll(playerScoreEntities);

        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();

    }

}
