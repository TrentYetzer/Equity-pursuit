import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHints } from 'app/shared/model/hints.model';

@Component({
  selector: 'jhi-hints-detail',
  templateUrl: './hints-detail.component.html'
})
export class HintsDetailComponent implements OnInit {
  hints: IHints | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hints }) => (this.hints = hints));
  }

  previousState(): void {
    window.history.back();
  }
}
