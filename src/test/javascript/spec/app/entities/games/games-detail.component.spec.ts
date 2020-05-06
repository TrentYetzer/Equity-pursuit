import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EquityPursuitTestModule } from '../../../test.module';
import { GamesDetailComponent } from 'app/entities/games/games-detail.component';
import { Games } from 'app/shared/model/games.model';

describe('Component Tests', () => {
  describe('Games Management Detail Component', () => {
    let comp: GamesDetailComponent;
    let fixture: ComponentFixture<GamesDetailComponent>;
    const route = ({ data: of({ games: new Games(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [GamesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GamesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GamesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load games on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.games).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
