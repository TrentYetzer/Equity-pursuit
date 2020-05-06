import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IListings } from 'app/shared/model/listings.model';

type EntityResponseType = HttpResponse<IListings>;
type EntityArrayResponseType = HttpResponse<IListings[]>;

@Injectable({ providedIn: 'root' })
export class ListingsService {
  public resourceUrl = SERVER_API_URL + 'api/listings';

  constructor(protected http: HttpClient) {}

  create(listings: IListings): Observable<EntityResponseType> {
    return this.http.post<IListings>(this.resourceUrl, listings, { observe: 'response' });
  }

  update(listings: IListings): Observable<EntityResponseType> {
    return this.http.put<IListings>(this.resourceUrl, listings, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IListings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IListings[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
