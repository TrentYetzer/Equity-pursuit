import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IListings } from 'app/shared/model/listings.model';
import { ListingsService } from './listings.service';
import { ListingsDeleteDialogComponent } from './listings-delete-dialog.component';

@Component({
  selector: 'jhi-listings',
  templateUrl: './listings.component.html'
})
export class ListingsComponent implements OnInit, OnDestroy {
  listings?: IListings[];
  eventSubscriber?: Subscription;

  constructor(protected listingsService: ListingsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.listingsService.query().subscribe((res: HttpResponse<IListings[]>) => (this.listings = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInListings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IListings): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInListings(): void {
    this.eventSubscriber = this.eventManager.subscribe('listingsListModification', () => this.loadAll());
  }

  delete(listings: IListings): void {
    const modalRef = this.modalService.open(ListingsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.listings = listings;
  }
}
