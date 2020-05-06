import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHints } from 'app/shared/model/hints.model';
import { HintsService } from './hints.service';

@Component({
  templateUrl: './hints-delete-dialog.component.html'
})
export class HintsDeleteDialogComponent {
  hints?: IHints;

  constructor(protected hintsService: HintsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hintsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('hintsListModification');
      this.activeModal.close();
    });
  }
}
