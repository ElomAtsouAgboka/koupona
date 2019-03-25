import { IPays } from 'app/shared/model/pays.model';
import { IQuartier } from 'app/shared/model/quartier.model';

export interface IVille {
    id?: number;
    nomVille?: string;
    pays?: IPays;
    quartiers?: IQuartier[];
}

export class Ville implements IVille {
    constructor(public id?: number, public nomVille?: string, public pays?: IPays, public quartiers?: IQuartier[]) {}
}
