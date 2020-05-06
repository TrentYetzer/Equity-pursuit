import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EquityPursuitSharedModule } from 'app/shared/shared.module';
import { ListingsComponent } from './listings.component';
import { ListingsDetailComponent } from './listings-detail.component';
import { ListingsUpdateComponent } from './listings-update.component';
import { ListingsDeleteDialogComponent } from './listings-delete-dialog.component';
import { listingsRoute } from './listings.route';

@NgModule({
  imports: [EquityPursuitSharedModule, RouterModule.forChild(listingsRoute)],
  declarations: [ListingsComponent, ListingsDetailComponent, ListingsUpdateComponent, ListingsDeleteDialogComponent],
  entryComponents: [ListingsDeleteDialogComponent]
})
export class EquityPursuitListingsModule {}
