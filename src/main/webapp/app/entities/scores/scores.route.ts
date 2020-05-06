import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IScores, Scores } from 'app/shared/model/scores.model';
import { ScoresService } from './scores.service';
import { ScoresComponent } from './scores.component';
import { ScoresDetailComponent } from './scores-detail.component';
import { ScoresUpdateComponent } from './scores-update.component';

@Injectable({ providedIn: 'root' })
export class ScoresResolve implements Resolve<IScores> {
  constructor(private service: ScoresService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScores> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((scores: HttpResponse<Scores>) => {
          if (scores.body) {
            return of(scores.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Scores());
  }
}

export const scoresRoute: Routes = [
  {
    path: '',
    component: ScoresComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.scores.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ScoresDetailComponent,
    resolve: {
      scores: ScoresResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.scores.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ScoresUpdateComponent,
    resolve: {
      scores: ScoresResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.scores.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ScoresUpdateComponent,
    resolve: {
      scores: ScoresResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.scores.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
