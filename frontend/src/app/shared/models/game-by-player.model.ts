import { Player } from './player.model';
import { Game } from './game.model';

export interface GameByPlayer {
  id: {
    gameId: number;
    playerId: number;
  };
  game: Game;
  player: Player;
}
