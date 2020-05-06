import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EquityPursuitTestModule } from '../../../test.module';
import { ScoresComponent } from 'app/entities/scores/scores.component';
import { ScoresService } from 'app/entities/scores/scores.service';
import { Scores } from 'app/shared/model/scores.model';

describe('Component Tests', () => {
  describe('Scores Management Component', () => {
    let comp: ScoresComponent;
    let fixture: ComponentFixture<ScoresComponent>;
    let service: ScoresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [ScoresComponent]
      })
        .overrideTemplate(ScoresComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScoresComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScoresService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Scores(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.scores && comp.scores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
