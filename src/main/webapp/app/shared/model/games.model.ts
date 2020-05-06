import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IGames {
  id?: number;
  playtime?: string;
  listingList?: string;
  score?: number;
  time?: Moment;
  user?: IUser;
}

export class Games implements IGames {
  constructor(
    public id?: number,
    public playtime?: string,
    public listingList?: string,
    public score?: number,
    public time?: Moment,
    public user?: IUser
  ) {}
}
