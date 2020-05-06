import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EquityPursuitTestModule } from '../../../test.module';
import { ScoresUpdateComponent } from 'app/entities/scores/scores-update.component';
import { ScoresService } from 'app/entities/scores/scores.service';
import { Scores } from 'app/shared/model/scores.model';

describe('Component Tests', () => {
  describe('Scores Management Update Component', () => {
    let comp: ScoresUpdateComponent;
    let fixture: ComponentFixture<ScoresUpdateComponent>;
    let service: ScoresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [ScoresUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ScoresUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScoresUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScoresService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Scores(123);
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
        const entity = new Scores();
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
