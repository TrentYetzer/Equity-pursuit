import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EquityPursuitTestModule } from '../../../test.module';
import { ListingsComponent } from 'app/entities/listings/listings.component';
import { ListingsService } from 'app/entities/listings/listings.service';
import { Listings } from 'app/shared/model/listings.model';

describe('Component Tests', () => {
  describe('Listings Management Component', () => {
    let comp: ListingsComponent;
    let fixture: ComponentFixture<ListingsComponent>;
    let service: ListingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [ListingsComponent]
      })
        .overrideTemplate(ListingsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ListingsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ListingsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Listings(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.listings && comp.listings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
