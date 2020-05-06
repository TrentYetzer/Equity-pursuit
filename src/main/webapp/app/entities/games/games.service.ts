import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGames } from 'app/shared/model/games.model';

type EntityResponseType = HttpResponse<IGames>;
type EntityArrayResponseType = HttpResponse<IGames[]>;

@Injectable({ providedIn: 'root' })
export class GamesService {
  public resourceUrl = SERVER_API_URL + 'api/games';

  constructor(protected http: HttpClient) {}

  create(games: IGames): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(games);
    return this.http
      .post<IGames>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(games: IGames): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(games);
    return this.http
      .put<IGames>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGames>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGames[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(games: IGames): IGames {
    const copy: IGames = Object.assign({}, games, {
      time: games.time && games.time.isValid() ? games.time.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.time = res.body.time ? moment(res.body.time) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((games: IGames) => {
        games.time = games.time ? moment(games.time) : undefined;
      });
    }
    return res;
  }
}
