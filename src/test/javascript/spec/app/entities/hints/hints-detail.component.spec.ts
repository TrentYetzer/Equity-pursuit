import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EquityPursuitTestModule } from '../../../test.module';
import { HintsDetailComponent } from 'app/entities/hints/hints-detail.component';
import { Hints } from 'app/shared/model/hints.model';

describe('Component Tests', () => {
  describe('Hints Management Detail Component', () => {
    let comp: HintsDetailComponent;
    let fixture: ComponentFixture<HintsDetailComponent>;
    const route = ({ data: of({ hints: new Hints(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [HintsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HintsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HintsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hints on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hints).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
