import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScores } from 'app/shared/model/scores.model';

@Component({
  selector: 'jhi-scores-detail',
  templateUrl: './scores-detail.component.html'
})
export class ScoresDetailComponent implements OnInit {
  scores: IScores | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scores }) => (this.scores = scores));
  }

  previousState(): void {
    window.history.back();
  }
}
