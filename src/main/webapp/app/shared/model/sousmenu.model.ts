import { IMenu } from 'app/shared/model/menu.model';

export interface ISousmenu {
    id?: number;
    sousMenuItem?: string;
    sousMenuItemImg?: string;
    menu?: IMenu;
}

export class Sousmenu implements ISousmenu {
    constructor(public id?: number, public sousMenuItem?: string, public sousMenuItemImg?: string, public menu?: IMenu) {}
}
