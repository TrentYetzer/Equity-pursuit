import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IScores } from 'app/shared/model/scores.model';
import { ScoresService } from './scores.service';
import { ScoresDeleteDialogComponent } from './scores-delete-dialog.component';

@Component({
  selector: 'jhi-scores',
  templateUrl: './scores.component.html'
})
export class ScoresComponent implements OnInit, OnDestroy {
  scores?: IScores[];
  eventSubscriber?: Subscription;

  constructor(protected scoresService: ScoresService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.scoresService.query().subscribe((res: HttpResponse<IScores[]>) => (this.scores = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInScores();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IScores): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInScores(): void {
    this.eventSubscriber = this.eventManager.subscribe('scoresListModification', () => this.loadAll());
  }

  delete(scores: IScores): void {
    const modalRef = this.modalService.open(ScoresDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.scores = scores;
  }
}
