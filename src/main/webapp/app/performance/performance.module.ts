import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EquityPursuitSharedModule } from 'app/shared/shared.module';
import { PERFORMANCE_ROUTE } from './performance.route';
import { PerformanceComponent } from './performance.component';

@NgModule({
  imports: [EquityPursuitSharedModule, RouterModule.forRoot([PERFORMANCE_ROUTE])],
  declarations: [PerformanceComponent],
  entryComponents: [],
  providers: []
})
export class PerformanceModule {}
