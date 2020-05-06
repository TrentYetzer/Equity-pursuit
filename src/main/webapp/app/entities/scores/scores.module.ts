import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EquityPursuitSharedModule } from 'app/shared/shared.module';
import { ScoresComponent } from './scores.component';
import { ScoresDetailComponent } from './scores-detail.component';
import { ScoresUpdateComponent } from './scores-update.component';
import { ScoresDeleteDialogComponent } from './scores-delete-dialog.component';
import { scoresRoute } from './scores.route';

@NgModule({
  imports: [EquityPursuitSharedModule, RouterModule.forChild(scoresRoute)],
  declarations: [ScoresComponent, ScoresDetailComponent, ScoresUpdateComponent, ScoresDeleteDialogComponent],
  entryComponents: [ScoresDeleteDialogComponent]
})
export class EquityPursuitScoresModule {}
