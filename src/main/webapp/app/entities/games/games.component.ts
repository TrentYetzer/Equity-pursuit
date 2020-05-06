import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGames } from 'app/shared/model/games.model';
import { GamesService } from './games.service';
import { GamesDeleteDialogComponent } from './games-delete-dialog.component';

@Component({
  selector: 'jhi-games',
  templateUrl: './games.component.html'
})
export class GamesComponent implements OnInit, OnDestroy {
  games?: IGames[];
  eventSubscriber?: Subscription;

  constructor(protected gamesService: GamesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.gamesService.query().subscribe((res: HttpResponse<IGames[]>) => (this.games = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGames();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGames): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGames(): void {
    this.eventSubscriber = this.eventManager.subscribe('gamesListModification', () => this.loadAll());
  }

  delete(games: IGames): void {
    const modalRef = this.modalService.open(GamesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.games = games;
  }
}
