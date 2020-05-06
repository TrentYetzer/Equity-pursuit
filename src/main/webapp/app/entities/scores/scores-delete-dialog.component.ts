import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScores } from 'app/shared/model/scores.model';
import { ScoresService } from './scores.service';

@Component({
  templateUrl: './scores-delete-dialog.component.html'
})
export class ScoresDeleteDialogComponent {
  scores?: IScores;

  constructor(protected scoresService: ScoresService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scoresService.delete(id).subscribe(() => {
      this.eventManager.broadcast('scoresListModification');
      this.activeModal.close();
    });
  }
}
