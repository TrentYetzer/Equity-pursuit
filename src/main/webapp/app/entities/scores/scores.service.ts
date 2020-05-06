import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IScores } from 'app/shared/model/scores.model';

type EntityResponseType = HttpResponse<IScores>;
type EntityArrayResponseType = HttpResponse<IScores[]>;

@Injectable({ providedIn: 'root' })
export class ScoresService {
  public resourceUrl = SERVER_API_URL + 'api/scores';

  constructor(protected http: HttpClient) {}

  create(scores: IScores): Observable<EntityResponseType> {
    return this.http.post<IScores>(this.resourceUrl, scores, { observe: 'response' });
  }

  update(scores: IScores): Observable<EntityResponseType> {
    return this.http.put<IScores>(this.resourceUrl, scores, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IScores>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScores[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
