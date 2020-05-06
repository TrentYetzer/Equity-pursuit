import { Route } from '@angular/router';

import { GameComponent } from './game.component';

export const GAME_ROUTE: Route = {
  path: 'game',
  component: GameComponent,
  data: {
    authorities: [],
    pageTitle: 'game.title'
  }
};
