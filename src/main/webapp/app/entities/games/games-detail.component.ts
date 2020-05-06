import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGames } from 'app/shared/model/games.model';

@Component({
  selector: 'jhi-games-detail',
  templateUrl: './games-detail.component.html'
})
export class GamesDetailComponent implements OnInit {
  games: IGames | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ games }) => (this.games = games));
  }

  previousState(): void {
    window.history.back();
  }
}
