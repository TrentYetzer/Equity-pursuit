import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EquityPursuitSharedModule } from 'app/shared/shared.module';
import { GamesComponent } from './games.component';
import { GamesDetailComponent } from './games-detail.component';
import { GamesUpdateComponent } from './games-update.component';
import { GamesDeleteDialogComponent } from './games-delete-dialog.component';
import { gamesRoute } from './games.route';

@NgModule({
  imports: [EquityPursuitSharedModule, RouterModule.forChild(gamesRoute)],
  declarations: [GamesComponent, GamesDetailComponent, GamesUpdateComponent, GamesDeleteDialogComponent],
  entryComponents: [GamesDeleteDialogComponent]
})
export class EquityPursuitGamesModule {}
