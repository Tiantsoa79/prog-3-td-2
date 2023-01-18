package app.foot.repository.mapper;

import app.foot.model.Player;
import app.foot.model.PlayerScorer;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.entity.PlayerScoreEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public Player toDomain(PlayerEntity entity) {
        return Player.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isGuardian(entity.isGuardian())
                .build();
    }
    public  PlayerEntity toRest(Player player) {
        return PlayerEntity.builder()
                .id(player.getId())
                .name(player.getName())
                .guardian(player.getIsGuardian())
                .build();
    }
    public PlayerScorer toDomain(PlayerScoreEntity entity) {
        return PlayerScorer.builder()
                .player(toDomain(entity.getPlayer()))
                .minute(entity.getMinute())
                .isOwnGoal(entity.isOwnGoal())
                .build();
    }

    public PlayerScoreEntity toDomain(PlayerScorer playerScorer) {
        return PlayerScoreEntity.builder()
                .player(toRest(playerScorer.getPlayer()))
                .minute(playerScorer.getMinute())
                .ownGoal(playerScorer.getIsOwnGoal())
                .build();
    }
}
