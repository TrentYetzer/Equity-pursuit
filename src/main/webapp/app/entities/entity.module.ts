import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'scores',
        loadChildren: () => import('./scores/scores.module').then(m => m.EquityPursuitScoresModule)
      },
      {
        path: 'listings',
        loadChildren: () => import('./listings/listings.module').then(m => m.EquityPursuitListingsModule)
      },
      {
        path: 'hints',
        loadChildren: () => import('./hints/hints.module').then(m => m.EquityPursuitHintsModule)
      },
      {
        path: 'games',
        loadChildren: () => import('./games/games.module').then(m => m.EquityPursuitGamesModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EquityPursuitEntityModule {}
