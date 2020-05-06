import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGames } from 'app/shared/model/games.model';
import { GamesService } from './games.service';

@Component({
  templateUrl: './games-delete-dialog.component.html'
})
export class GamesDeleteDialogComponent {
  games?: IGames;

  constructor(protected gamesService: GamesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gamesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gamesListModification');
      this.activeModal.close();
    });
  }
}
