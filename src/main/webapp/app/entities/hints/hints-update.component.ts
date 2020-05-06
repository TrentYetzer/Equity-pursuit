import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHints, Hints } from 'app/shared/model/hints.model';
import { HintsService } from './hints.service';
import { IListings } from 'app/shared/model/listings.model';
import { ListingsService } from 'app/entities/listings/listings.service';

@Component({
  selector: 'jhi-hints-update',
  templateUrl: './hints-update.component.html'
})
export class HintsUpdateComponent implements OnInit {
  isSaving = false;
  listings: IListings[] = [];

  editForm = this.fb.group({
    id: [],
    hint: [null, [Validators.required]],
    modifier: [null, [Validators.required]],
    listings: [null, Validators.required]
  });

  constructor(
    protected hintsService: HintsService,
    protected listingsService: ListingsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hints }) => {
      this.updateForm(hints);

      this.listingsService.query().subscribe((res: HttpResponse<IListings[]>) => (this.listings = res.body || []));
    });
  }

  updateForm(hints: IHints): void {
    this.editForm.patchValue({
      id: hints.id,
      hint: hints.hint,
      modifier: hints.modifier,
      listings: hints.listings
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hints = this.createFromForm();
    if (hints.id !== undefined) {
      this.subscribeToSaveResponse(this.hintsService.update(hints));
    } else {
      this.subscribeToSaveResponse(this.hintsService.create(hints));
    }
  }

  private createFromForm(): IHints {
    return {
      ...new Hints(),
      id: this.editForm.get(['id'])!.value,
      hint: this.editForm.get(['hint'])!.value,
      modifier: this.editForm.get(['modifier'])!.value,
      listings: this.editForm.get(['listings'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHints>>): void {
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

  trackById(index: number, item: IListings): any {
    return item.id;
  }
}
