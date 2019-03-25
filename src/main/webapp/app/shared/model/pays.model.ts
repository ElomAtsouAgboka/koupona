import { IVille } from 'app/shared/model/ville.model';

export interface IPays {
    id?: number;
    nomPays?: string;
    villes?: IVille[];
}

export class Pays implements IPays {
    constructor(public id?: number, public nomPays?: string, public villes?: IVille[]) {}
}
