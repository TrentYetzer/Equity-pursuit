import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ListingsService } from 'app/entities/listings/listings.service';
import { IListings, Listings } from 'app/shared/model/listings.model';

describe('Service Tests', () => {
  describe('Listings Service', () => {
    let injector: TestBed;
    let service: ListingsService;
    let httpMock: HttpTestingController;
    let elemDefault: IListings;
    let expectedResult: IListings | IListings[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ListingsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Listings(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Listings', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Listings()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Listings', () => {
        const returnedFromService = Object.assign(
          {
            zpid: 1,
            street: 'BBBBBB',
            city: 'BBBBBB',
            state: 'BBBBBB',
            zipCode: 1,
            zestimate: 1,
            address: 1,
            longitude: 1,
            latitude: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Listings', () => {
        const returnedFromService = Object.assign(
          {
            zpid: 1,
            street: 'BBBBBB',
            city: 'BBBBBB',
            state: 'BBBBBB',
            zipCode: 1,
            zestimate: 1,
            address: 1,
            longitude: 1,
            latitude: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Listings', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
