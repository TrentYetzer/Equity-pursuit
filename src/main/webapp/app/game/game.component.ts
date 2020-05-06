import { Component, OnInit, ViewChild } from '@angular/core';
import { GoogleMap } from '@angular/google-maps';
import { GameService } from '../GameService.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Listings } from '../Listings';
import { Hints } from '../Hints';
import { User } from 'app/core/user/user.model';

export type GameType = 'inProgress' | 'finished' | '';

@Component({
  selector: 'jhi-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})
export class GameComponent implements OnInit {
  @ViewChild(GoogleMap, { static: false }) map: GoogleMap;

  zoom = 18;
  center: google.maps.LatLngLiteral;
  options: google.maps.MapOptions = {
    disableDoubleClickZoom: true,
    mapTypeId: 'hybrid',
    maxZoom: 20,
    minZoom: 5
  };
  markers = [];
  mapProgress = false;

  startTime = Date.now();
  inputBox = '';
  position = 0;
  listingCount = 10;
  playlist: Listings[] = [];
  hints: Hints[] = [];
  displayHints: string[] = [];
  currentModifier = 1;
  gameProgress: GameType = 'inProgress';
  currentUserID = 0;
  finalScore = 0;

  gameForm = this.fb.group({
    guess: ['', Validators.required]
  });

  constructor(private gameService: GameService, private fb: FormBuilder) {
    this.findCurrentUser();
    this.position = 0;
    this.position = JSON.parse(localStorage.getItem('Position') || '0');
    localStorage.removeItem('Position');
    this.playlist = JSON.parse(localStorage.getItem('Playlist') || '[]');
    localStorage.removeItem('Playlist');
    if (this.playlist.length <= 0) {
      this.createPlaylist();
    }
  }

  ngOnInit(): void {
    setTimeout(() => {
      // eslint-disable-next-line no-console
      console.log(this.playlist[0]);
      this.center = {
        lat: this.playlist[0].latitude,
        lng: this.playlist[0].longitude
      };
      this.initializeMap(this.playlist[this.position]);
    }, 500);
  }

  createPlaylist(): void {
    this.gameService.generatePLaylist().subscribe((data: Listings[]) => {
      const newPlaylist: Listings[] = [];
      for (let i = 0; i < 5; i++) {
        let randomNum = Math.floor(Math.random() * Math.floor(this.listingCount));
        while (newPlaylist.includes(data[randomNum])) {
          randomNum = Math.floor(Math.random() * Math.floor(this.listingCount));
        }
        newPlaylist.push(data[randomNum]);
      }
      localStorage.removeItem('Playlist');
      localStorage.setItem('Playlist', JSON.stringify(newPlaylist));
      this.playlist = newPlaylist;
    });
  }

  findCurrentUser(): void {
    this.gameService.getUserID().subscribe((data: User) => {
      const currentID = data.id;
      // eslint-disable-next-line no-console
      console.log(currentID);
      this.currentUserID = currentID;
      // eslint-disable-next-line no-console
      console.log(this.currentUserID);
    });
  }

  onSubmit(guess: number): void {
    this.inputBox = '';
    this.displayHints = [];
    this.position++;
    localStorage.removeItem('Position');
    localStorage.setItem('Position', JSON.stringify(this.position));
    if (this.position >= 5) {
      this.gameProgress = 'finished';
      this.mapProgress = false;
      const timeElapsed = ((Date.now() - this.startTime) / 60000).toFixed(2);
      this.gameService.createGame(this.playlist, this.currentUserID, this.finalScore, timeElapsed);
    } else {
      this.initializeMap(this.playlist[this.position]);
      this.finalScore += this.gameService.checkGuess(guess, this.currentModifier, this.playlist, this.position);
    }
    this.currentModifier = 1;
  }

  get showInProgress(): boolean {
    return this.gameProgress === 'inProgress';
  }

  get showFinished(): boolean {
    return this.gameProgress === 'finished';
  }

  get showMap(): boolean {
    return this.mapProgress;
  }

  playAgain(): void {
    this.createPlaylist();
    this.position = 0;
    this.gameProgress = 'inProgress';
    this.initializeMap(this.playlist[0]);
  }

  initializeMap(list: Listings): void {
    setTimeout(() => {
      // eslint-disable-next-line no-console
      console.log(list.longitude);
      this.center = {
        lat: list.latitude,
        lng: list.longitude
      };
      this.addMarker(list);
      this.getHints(list);
      this.mapProgress = true;
    }, 500);
  }

  addMarker(list: Listings): void {
    this.markers.pop();
    this.markers.push({
      position: {
        lat: list.latitude,
        lng: list.longitude
      },
      title: list.address + ' ' + list.street + ' ' + list.city + ', ' + list.state + ' ' + list.zipCode,
      options: {
        animation: google.maps.Animation.DROP
      }
    });
  }

  getHints(list: Listings): void {
    this.gameService.getHint(list.id).subscribe((data: Hints[]) => {
      this.hints = data.sort((a, b) => (a.modifier > b.modifier ? 1 : -1));
    });
  }

  hintButton(): void {
    const outHint = this.hints.pop();
    this.displayHints.push(outHint.hint);
    this.currentModifier *= outHint.modifier;
  }
}
