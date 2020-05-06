import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHints } from 'app/shared/model/hints.model';

type EntityResponseType = HttpResponse<IHints>;
type EntityArrayResponseType = HttpResponse<IHints[]>;

@Injectable({ providedIn: 'root' })
export class HintsService {
  public resourceUrl = SERVER_API_URL + 'api/hints';

  constructor(protected http: HttpClient) {}

  create(hints: IHints): Observable<EntityResponseType> {
    return this.http.post<IHints>(this.resourceUrl, hints, { observe: 'response' });
  }

  update(hints: IHints): Observable<EntityResponseType> {
    return this.http.put<IHints>(this.resourceUrl, hints, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHints>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHints[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
