import { IGames } from 'app/shared/model/games.model';

export interface IScores {
  id?: number;
  score?: number;
  games?: IGames;
}

export class Scores implements IScores {
  constructor(public id?: number, public score?: number, public games?: IGames) {}
}
