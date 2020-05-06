import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHints } from 'app/shared/model/hints.model';
import { HintsService } from './hints.service';
import { HintsDeleteDialogComponent } from './hints-delete-dialog.component';

@Component({
  selector: 'jhi-hints',
  templateUrl: './hints.component.html'
})
export class HintsComponent implements OnInit, OnDestroy {
  hints?: IHints[];
  eventSubscriber?: Subscription;

  constructor(protected hintsService: HintsService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.hintsService.query().subscribe((res: HttpResponse<IHints[]>) => (this.hints = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHints();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHints): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHints(): void {
    this.eventSubscriber = this.eventManager.subscribe('hintsListModification', () => this.loadAll());
  }

  delete(hints: IHints): void {
    const modalRef = this.modalService.open(HintsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hints = hints;
  }
}
