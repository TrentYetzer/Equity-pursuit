import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EquityPursuitSharedModule } from 'app/shared/shared.module';
import { PROFILE_ROUTE } from './profile.route';
import { ProfileComponent } from './profile.component';

@NgModule({
  imports: [EquityPursuitSharedModule, RouterModule.forRoot([PROFILE_ROUTE])],
  declarations: [ProfileComponent],
  entryComponents: [],
  providers: []
})
export class ProfileModule {}
