import { Component, OnInit } from '@angular/core';
import { Games } from '../Games';
import { User } from 'app/core/user/user.model';
import { PerformanceService } from '../performance.service';

@Component({
  selector: 'jhi-performance',
  templateUrl: './performance.component.html',
  styleUrls: ['./performance.component.scss']
})
export class PerformanceComponent implements OnInit {
  gameList: Games[] = [];
  currentUserID: number;

  constructor(private performance: PerformanceService) {
    this.findCurrentUser();
    setTimeout(() => {
      this.getGames();
    }, 1000);
  }

  ngOnInit(): void {}

  findCurrentUser(): void {
    this.performance.getUserID().subscribe((data: User) => {
      const currentID = data.id;
      // eslint-disable-next-line no-console
      console.log(currentID);
      this.currentUserID = currentID;
      // eslint-disable-next-line no-console
      console.log(this.currentUserID);
    });
  }

  getGames(): void {
    this.performance.getGames(this.currentUserID).subscribe((data: Games[]) => {
      this.gameList = data;
    });
  }

  sortId(): void {
    this.gameList = this.gameList.sort((a, b) => (a.id > b.id ? 1 : -1));
  }

  sortScores(): void {
    this.gameList = this.gameList.sort((a, b) => (a.score > b.score ? 1 : -1));
  }

  sortPlaytime(): void {
    this.gameList = this.gameList.sort((a, b) => (a.playtime > b.playtime ? 1 : -1));
  }

  sortTime(): void {
    this.gameList = this.gameList.sort((a, b) => (a.time > b.time ? 1 : -1));
  }
}
