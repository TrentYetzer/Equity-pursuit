export interface IListings {
  id?: number;
  zpid?: number;
  street?: string;
  city?: string;
  state?: string;
  zipCode?: number;
  zestimate?: number;
  address?: number;
  longitude?: number;
  latitude?: number;
}

export class Listings implements IListings {
  constructor(
    public id?: number,
    public zpid?: number,
    public street?: string,
    public city?: string,
    public state?: string,
    public zipCode?: number,
    public zestimate?: number,
    public address?: number,
    public longitude?: number,
    public latitude?: number
  ) {}
}
