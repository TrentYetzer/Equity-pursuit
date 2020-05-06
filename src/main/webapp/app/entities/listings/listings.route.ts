import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IListings, Listings } from 'app/shared/model/listings.model';
import { ListingsService } from './listings.service';
import { ListingsComponent } from './listings.component';
import { ListingsDetailComponent } from './listings-detail.component';
import { ListingsUpdateComponent } from './listings-update.component';

@Injectable({ providedIn: 'root' })
export class ListingsResolve implements Resolve<IListings> {
  constructor(private service: ListingsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IListings> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((listings: HttpResponse<Listings>) => {
          if (listings.body) {
            return of(listings.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Listings());
  }
}

export const listingsRoute: Routes = [
  {
    path: '',
    component: ListingsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.listings.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ListingsDetailComponent,
    resolve: {
      listings: ListingsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.listings.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ListingsUpdateComponent,
    resolve: {
      listings: ListingsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.listings.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ListingsUpdateComponent,
    resolve: {
      listings: ListingsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'equityPursuitApp.listings.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
