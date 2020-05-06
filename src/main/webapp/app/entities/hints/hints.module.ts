import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EquityPursuitSharedModule } from 'app/shared/shared.module';
import { HintsComponent } from './hints.component';
import { HintsDetailComponent } from './hints-detail.component';
import { HintsUpdateComponent } from './hints-update.component';
import { HintsDeleteDialogComponent } from './hints-delete-dialog.component';
import { hintsRoute } from './hints.route';

@NgModule({
  imports: [EquityPursuitSharedModule, RouterModule.forChild(hintsRoute)],
  declarations: [HintsComponent, HintsDetailComponent, HintsUpdateComponent, HintsDeleteDialogComponent],
  entryComponents: [HintsDeleteDialogComponent]
})
export class EquityPursuitHintsModule {}
