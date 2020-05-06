import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGames, Games } from 'app/shared/model/games.model';
import { GamesService } from './games.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-games-update',
  templateUrl: './games-update.component.html'
})
export class GamesUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    playtime: [null, [Validators.required]],
    listingList: [null, [Validators.required]],
    score: [null, [Validators.required]],
    time: [null, [Validators.required]],
    user: [null, Validators.required]
  });

  constructor(
    protected gamesService: GamesService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ games }) => {
      if (!games.id) {
        const today = moment().startOf('day');
        games.time = today;
      }

      this.updateForm(games);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(games: IGames): void {
    this.editForm.patchValue({
      id: games.id,
      playtime: games.playtime,
      listingList: games.listingList,
      score: games.score,
      time: games.time ? games.time.format(DATE_TIME_FORMAT) : null,
      user: games.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const games = this.createFromForm();
    if (games.id !== undefined) {
      this.subscribeToSaveResponse(this.gamesService.update(games));
    } else {
      this.subscribeToSaveResponse(this.gamesService.create(games));
    }
  }

  private createFromForm(): IGames {
    return {
      ...new Games(),
      id: this.editForm.get(['id'])!.value,
      playtime: this.editForm.get(['playtime'])!.value,
      listingList: this.editForm.get(['listingList'])!.value,
      score: this.editForm.get(['score'])!.value,
      time: this.editForm.get(['time'])!.value ? moment(this.editForm.get(['time'])!.value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGames>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
