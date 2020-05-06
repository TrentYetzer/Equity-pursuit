import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListings } from 'app/shared/model/listings.model';

@Component({
  selector: 'jhi-listings-detail',
  templateUrl: './listings-detail.component.html'
})
export class ListingsDetailComponent implements OnInit {
  listings: IListings | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listings }) => (this.listings = listings));
  }

  previousState(): void {
    window.history.back();
  }
}
