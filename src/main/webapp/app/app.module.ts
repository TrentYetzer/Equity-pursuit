import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';

import './vendor';
import { EquityPursuitSharedModule } from 'app/shared/shared.module';
import { EquityPursuitCoreModule } from 'app/core/core.module';
import { EquityPursuitAppRoutingModule } from './app-routing.module';
import { EquityPursuitHomeModule } from './home/home.module';
import { EquityPursuitEntityModule } from './entities/entity.module';
import { GameModule } from './game/game.module';
import { PerformanceModule } from './performance/performance.module';
import { ProfileModule } from './profile/profile.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    EquityPursuitSharedModule,
    EquityPursuitCoreModule,
    EquityPursuitHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    EquityPursuitEntityModule,
    EquityPursuitAppRoutingModule,
    GameModule,
    PerformanceModule,
    ProfileModule,
    ReactiveFormsModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class EquityPursuitAppModule {}
