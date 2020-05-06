import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { GoogleMapsModule } from '@angular/google-maps';

import { EquityPursuitSharedModule } from 'app/shared/shared.module';
import { GAME_ROUTE } from './game.route';
import { GameComponent } from './game.component';

@NgModule({
  imports: [EquityPursuitSharedModule, RouterModule.forRoot([GAME_ROUTE]), HttpClientModule, GoogleMapsModule],
  declarations: [GameComponent],
  entryComponents: [],
  providers: []
})
export class GameModule {}
