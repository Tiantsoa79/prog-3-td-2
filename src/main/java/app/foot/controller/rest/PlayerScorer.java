package app.foot.controller.rest;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@ToString
@Setter
public class PlayerScorer {
  private Player player;
  private Integer scoreTime;
  private Boolean isOG;
}
