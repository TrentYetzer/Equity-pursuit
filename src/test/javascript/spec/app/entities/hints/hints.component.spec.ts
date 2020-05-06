import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EquityPursuitTestModule } from '../../../test.module';
import { HintsComponent } from 'app/entities/hints/hints.component';
import { HintsService } from 'app/entities/hints/hints.service';
import { Hints } from 'app/shared/model/hints.model';

describe('Component Tests', () => {
  describe('Hints Management Component', () => {
    let comp: HintsComponent;
    let fixture: ComponentFixture<HintsComponent>;
    let service: HintsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [HintsComponent]
      })
        .overrideTemplate(HintsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HintsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HintsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Hints(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.hints && comp.hints[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
