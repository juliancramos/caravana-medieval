export interface GameByPlayer {
    id: {
      gameId: number;
      playerId: number;
    };
    game: {
      idGame: number;
      map: {
        idMap: number;
        name: string;
        description: string;
        imgUrl: string | null;
      };
    };
    player: {
      idPlayer: number;
      username: string;
      imgUrl: string | null;
    };
  }
  