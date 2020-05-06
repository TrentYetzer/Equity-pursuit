import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EquityPursuitTestModule } from '../../../test.module';
import { GamesComponent } from 'app/entities/games/games.component';
import { GamesService } from 'app/entities/games/games.service';
import { Games } from 'app/shared/model/games.model';

describe('Component Tests', () => {
  describe('Games Management Component', () => {
    let comp: GamesComponent;
    let fixture: ComponentFixture<GamesComponent>;
    let service: GamesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [GamesComponent]
      })
        .overrideTemplate(GamesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GamesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GamesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Games(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.games && comp.games[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
