package app.foot.service;

import app.foot.model.Match;
import app.foot.model.PlayerScorer;
import app.foot.repository.MatchRepository;
import app.foot.repository.PlayerRepository;
import app.foot.repository.PlayerScorerRepository;
import app.foot.repository.entity.MatchEntity;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.entity.PlayerScoreEntity;
import app.foot.repository.mapper.MatchMapper;
import app.foot.repository.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchService {
    private final MatchRepository repository;
    private final MatchMapper mapper;
    private final PlayerMapper playerMapper;

    private final PlayerRepository playerRepository;


    private final PlayerScorerRepository playerScorerRepository;
    public PlayerEntity getPlayerById(int id){
        return playerRepository.findById(id).get();
    }
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

        for(PlayerScoreEntity p : playerScoreEntities){

            PlayerEntity player = playerRepository.findById(p.getPlayer().getId()).get();

            if (player.isGuardian()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Error: player with id :", p.getPlayer().getId(), "is a guardian"));
            }else   if (p.getMinute()<1 || p.getMinute()>90){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Error:  score time must be between 0 and 90"));
            }else {
               p.setPlayer(player);
               p.setMatch(match);
            }

        }
        playerScorerRepository.saveAll(playerScoreEntities);

        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
        /*
        for(PlayerScorer p : playerScorers){

            PlayerScoreEntity scorer = new PlayerScoreEntity();

            PlayerEntity player = playerRepository.findById(p.getPlayer().getId()).get();

            if (p.getPlayer().getIsGuardian()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Error: player with id :", p.getPlayer().getId(), "is a guardian"));
            }else   if (p.getMinute()<1 || p.getMinute()>90){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Error:  score time must be between 0 and 90"));
            }else {
                scorer.setPlayer(player);
                scorer.setOwnGoal(p.getIsOwnGoal());
                scorer.setMinute(p.getMinute());
                scorer.setMatch(match);

                toSave.add(scorer);
            }

        }
        playerScorerRepository.saveAll(toSave);


        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();

       List<PlayerScoreEntity> playerScoreEntities = playerScorers.stream()
             .map(playerMapper::toDomain)
             .toList();


        List<PlayerScoreEntity> toSave = new ArrayList<>();

        match.getScorers().addAll(toSave);

        */

    }

}
