import { IVille } from 'app/shared/model/ville.model';

export interface IQuartier {
    id?: number;
    nomQuartier?: string;
    ville?: IVille;
}

export class Quartier implements IQuartier {
    constructor(public id?: number, public nomQuartier?: string, public ville?: IVille) {}
}
