import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EquityPursuitTestModule } from '../../../test.module';
import { HintsUpdateComponent } from 'app/entities/hints/hints-update.component';
import { HintsService } from 'app/entities/hints/hints.service';
import { Hints } from 'app/shared/model/hints.model';

describe('Component Tests', () => {
  describe('Hints Management Update Component', () => {
    let comp: HintsUpdateComponent;
    let fixture: ComponentFixture<HintsUpdateComponent>;
    let service: HintsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [HintsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HintsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HintsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HintsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Hints(123);
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
        const entity = new Hints();
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
