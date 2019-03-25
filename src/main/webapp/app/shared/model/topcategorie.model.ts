import { ICategorie } from 'app/shared/model/categorie.model';

export interface ITopcategorie {
    id?: number;
    nomTopCategorie?: string;
    categories?: ICategorie[];
}

export class Topcategorie implements ITopcategorie {
    constructor(public id?: number, public nomTopCategorie?: string, public categories?: ICategorie[]) {}
}
