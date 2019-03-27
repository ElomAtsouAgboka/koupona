import { ITopcategorie } from 'app/shared/model/topcategorie.model';
import { ISouscategorie } from 'app/shared/model/souscategorie.model';

export interface ICategorie {
    id?: number;
    nomCategorie?: string;
    topcategorie?: ITopcategorie;
    souscategories?: ISouscategorie[];
}

export class Categorie implements ICategorie {
    constructor(
        public id?: number,
        public nomCategorie?: string,
        public topcategorie?: ITopcategorie,
        public souscategories?: ISouscategorie[]
    ) {}
}
