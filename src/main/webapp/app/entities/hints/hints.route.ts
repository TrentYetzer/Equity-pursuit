import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHints, Hints } from 'app/shared/model/hints.model';
import { HintsService } from './hints.service';
import { HintsComponent } from './hints.component';
import { HintsDetailComponent } from './hints-detail.component';
import { HintsUpdateComponent } from './hints-update.component';

@Injectable({ providedIn: 'root' })
export class HintsResolve implements Resolve<IHints> {
  constructor(private service: HintsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHints> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((hints: HttpResponse<Hints>) => {
          if (hints.body) {
            return of(hints.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Hints());
  }
}

export const hintsRoute: Routes = [
  {
    path: '',
    component: HintsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.hints.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HintsDetailComponent,
    resolve: {
      hints: HintsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.hints.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HintsUpdateComponent,
    resolve: {
      hints: HintsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.hints.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HintsUpdateComponent,
    resolve: {
      hints: HintsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.hints.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
