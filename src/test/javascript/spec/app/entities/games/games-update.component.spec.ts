import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EquityPursuitTestModule } from '../../../test.module';
import { GamesUpdateComponent } from 'app/entities/games/games-update.component';
import { GamesService } from 'app/entities/games/games.service';
import { Games } from 'app/shared/model/games.model';

describe('Component Tests', () => {
  describe('Games Management Update Component', () => {
    let comp: GamesUpdateComponent;
    let fixture: ComponentFixture<GamesUpdateComponent>;
    let service: GamesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [GamesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GamesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GamesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GamesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Games(123);
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
        const entity = new Games();
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
