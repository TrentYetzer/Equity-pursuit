import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EquityPursuitTestModule } from '../../../test.module';
import { ListingsUpdateComponent } from 'app/entities/listings/listings-update.component';
import { ListingsService } from 'app/entities/listings/listings.service';
import { Listings } from 'app/shared/model/listings.model';

describe('Component Tests', () => {
  describe('Listings Management Update Component', () => {
    let comp: ListingsUpdateComponent;
    let fixture: ComponentFixture<ListingsUpdateComponent>;
    let service: ListingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [ListingsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ListingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ListingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ListingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Listings(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Listings();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
