import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IListings } from 'app/shared/model/listings.model';
import { ListingsService } from './listings.service';

@Component({
  templateUrl: './listings-delete-dialog.component.html'
})
export class ListingsDeleteDialogComponent {
  listings?: IListings;

  constructor(protected listingsService: ListingsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.listingsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('listingsListModification');
      this.activeModal.close();
    });
  }
}
