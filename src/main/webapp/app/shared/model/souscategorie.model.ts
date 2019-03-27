import { ICategorie } from 'app/shared/model/categorie.model';

export interface ISouscategorie {
    id?: number;
    nomSousCategorie?: string;
    categorie?: ICategorie;
}

export class Souscategorie implements ISouscategorie {
    constructor(public id?: number, public nomSousCategorie?: string, public categorie?: ICategorie) {}
}
