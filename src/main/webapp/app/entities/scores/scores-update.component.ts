import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IScores, Scores } from 'app/shared/model/scores.model';
import { ScoresService } from './scores.service';
import { IGames } from 'app/shared/model/games.model';
import { GamesService } from 'app/entities/games/games.service';

@Component({
  selector: 'jhi-scores-update',
  templateUrl: './scores-update.component.html'
})
export class ScoresUpdateComponent implements OnInit {
  isSaving = false;
  games: IGames[] = [];

  editForm = this.fb.group({
    id: [],
    score: [],
    games: [null, Validators.required]
  });

  constructor(
    protected scoresService: ScoresService,
    protected gamesService: GamesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scores }) => {
      this.updateForm(scores);

      this.gamesService.query().subscribe((res: HttpResponse<IGames[]>) => (this.games = res.body || []));
    });
  }

  updateForm(scores: IScores): void {
    this.editForm.patchValue({
      id: scores.id,
      score: scores.score,
      games: scores.games
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scores = this.createFromForm();
    if (scores.id !== undefined) {
      this.subscribeToSaveResponse(this.scoresService.update(scores));
    } else {
      this.subscribeToSaveResponse(this.scoresService.create(scores));
    }
  }

  private createFromForm(): IScores {
    return {
      ...new Scores(),
      id: this.editForm.get(['id'])!.value,
      score: this.editForm.get(['score'])!.value,
      games: this.editForm.get(['games'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScores>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IGames): any {
    return item.id;
  }
}
