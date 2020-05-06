import { IListings } from 'app/shared/model/listings.model';

export interface IHints {
  id?: number;
  hint?: string;
  modifier?: number;
  listings?: IListings;
}

export class Hints implements IHints {
  constructor(public id?: number, public hint?: string, public modifier?: number, public listings?: IListings) {}
}
