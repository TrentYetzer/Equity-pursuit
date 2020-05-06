import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EquityPursuitTestModule } from '../../../test.module';
import { ScoresDetailComponent } from 'app/entities/scores/scores-detail.component';
import { Scores } from 'app/shared/model/scores.model';

describe('Component Tests', () => {
  describe('Scores Management Detail Component', () => {
    let comp: ScoresDetailComponent;
    let fixture: ComponentFixture<ScoresDetailComponent>;
    const route = ({ data: of({ scores: new Scores(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [ScoresDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ScoresDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScoresDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load scores on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.scores).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
