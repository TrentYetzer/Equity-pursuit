import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IListings, Listings } from 'app/shared/model/listings.model';
import { ListingsService } from './listings.service';

@Component({
  selector: 'jhi-listings-update',
  templateUrl: './listings-update.component.html'
})
export class ListingsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    zpid: [null, [Validators.required]],
    street: [null, [Validators.required]],
    city: [null, [Validators.required]],
    state: [null, [Validators.required]],
    zipCode: [null, [Validators.required]],
    zestimate: [null, [Validators.required, Validators.min(0)]],
    address: [null, [Validators.required]],
    longitude: [null, [Validators.required]],
    latitude: [null, [Validators.required]]
  });

  constructor(protected listingsService: ListingsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listings }) => {
      this.updateForm(listings);
    });
  }

  updateForm(listings: IListings): void {
    this.editForm.patchValue({
      id: listings.id,
      zpid: listings.zpid,
      street: listings.street,
      city: listings.city,
      state: listings.state,
      zipCode: listings.zipCode,
      zestimate: listings.zestimate,
      address: listings.address,
      longitude: listings.longitude,
      latitude: listings.latitude
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const listings = this.createFromForm();
    if (listings.id !== undefined) {
      this.subscribeToSaveResponse(this.listingsService.update(listings));
    } else {
      this.subscribeToSaveResponse(this.listingsService.create(listings));
    }
  }

  private createFromForm(): IListings {
    return {
      ...new Listings(),
      id: this.editForm.get(['id'])!.value,
      zpid: this.editForm.get(['zpid'])!.value,
      street: this.editForm.get(['street'])!.value,
      city: this.editForm.get(['city'])!.value,
      state: this.editForm.get(['state'])!.value,
      zipCode: this.editForm.get(['zipCode'])!.value,
      zestimate: this.editForm.get(['zestimate'])!.value,
      address: this.editForm.get(['address'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      latitude: this.editForm.get(['latitude'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IListings>>): void {
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
}
