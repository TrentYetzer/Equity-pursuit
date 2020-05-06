import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EquityPursuitTestModule } from '../../../test.module';
import { ListingsDetailComponent } from 'app/entities/listings/listings-detail.component';
import { Listings } from 'app/shared/model/listings.model';

describe('Component Tests', () => {
  describe('Listings Management Detail Component', () => {
    let comp: ListingsDetailComponent;
    let fixture: ComponentFixture<ListingsDetailComponent>;
    const route = ({ data: of({ listings: new Listings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EquityPursuitTestModule],
        declarations: [ListingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ListingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ListingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load listings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.listings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
