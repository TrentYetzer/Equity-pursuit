import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Listings } from './Listings';
import { Games } from './Games';
import { Scores } from './Scores';
import { Hints } from './Hints';
import { User } from 'app/core/user/user.model';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'Basic ' + btoa('admin:admin')
  })
};

export type GameType = 'inProgress' | 'finished' | '';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  constructor(private http: HttpClient) {}

  getUserID(): Observable<User> {
    return this.http.get<User>('/api/account', httpOptions);
  }

  // getListingCount(): number {
  //   let listingCount = 0;
  //   this.http.get<number>('/api/listings/count').subscribe(data => {
  //     listingCount = data;
  //   });
  //   return listingCount;
  // }

  generatePLaylist(): Observable<Listings[]> {
    return this.http.get<Listings[]>(`/api/listings`, httpOptions);
  }

  createGame(playlist: Listings[], currentID: number, finalScore: number, playTime: string): void {
    const currentDate = new Date();
    let listings = playlist[0].id + '';
    for (let i = 1; i < 5; i++) {
      listings += ', ' + playlist[i].id;
    }
    this.http
      .post<Games>(
        '/api/games',
        {
          listingList: listings,
          time: currentDate,
          playtime: playTime + ' minutes',
          score: finalScore,
          user: {
            id: currentID
          }
        },
        httpOptions
      )
      .subscribe({
        error: error => console.error('There was an error!', error)
      });
  }

  checkGuess(guess: number, modifier: number, playlist: Listings[], position: number): number {
    let guessScore = Math.round(5000 - Math.abs(playlist[position].zestimate - guess) / 1000) * modifier;
    if (guessScore < 0) guessScore = 0;
    // eslint-disable-next-line no-console
    console.log(guessScore);
    this.http
      .post<Scores>(
        '/api/scores',
        {
          score: guessScore,
          games: {
            id: 3
          }
        },
        httpOptions
      )
      .subscribe({
        error: error => console.error('There was an error!', error)
      });
    return guessScore;
  }

  getHint(listingID: number): Observable<Hints[]> {
    return this.http.get<Hints[]>(`/api/hints?listingsId.equals=${listingID}`, httpOptions);
  }
}
