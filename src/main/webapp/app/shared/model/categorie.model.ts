import { ITopcategorie } from 'app/shared/model/topcategorie.model';

export interface ICategorie {
    id?: number;
    nomCategorie?: string;
    topcategorie?: ITopcategorie;
}

export class Categorie implements ICategorie {
    constructor(public id?: number, public nomCategorie?: string, public topcategorie?: ITopcategorie) {}
}
