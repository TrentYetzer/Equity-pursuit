import { Injectable } from '@angular/core';
import { Games } from './Games';
import { User } from 'app/core/user/user.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Basic ' + btoa('admin:admin')
  })
};

@Injectable({
  providedIn: 'root'
})
export class PerformanceService {
  constructor(private http: HttpClient) {}

  getUserID(): Observable<User> {
    return this.http.get<User>('/api/account', httpOptions);
  }

  getGames(currentId: number): Observable<Games[]> {
    return this.http.get<Games[]>(`api/games?userId.equals=${currentId}`, httpOptions);
  }
}
