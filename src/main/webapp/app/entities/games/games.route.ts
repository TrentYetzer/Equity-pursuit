import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGames, Games } from 'app/shared/model/games.model';
import { GamesService } from './games.service';
import { GamesComponent } from './games.component';
import { GamesDetailComponent } from './games-detail.component';
import { GamesUpdateComponent } from './games-update.component';

@Injectable({ providedIn: 'root' })
export class GamesResolve implements Resolve<IGames> {
  constructor(private service: GamesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGames> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((games: HttpResponse<Games>) => {
          if (games.body) {
            return of(games.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Games());
  }
}

export const gamesRoute: Routes = [
  {
    path: '',
    component: GamesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.games.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GamesDetailComponent,
    resolve: {
      games: GamesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.games.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GamesUpdateComponent,
    resolve: {
      games: GamesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.games.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GamesUpdateComponent,
    resolve: {
      games: GamesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.games.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
