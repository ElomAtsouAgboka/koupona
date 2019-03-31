import { ISousmenu } from 'app/shared/model/sousmenu.model';

export interface IMenu {
    id?: number;
    menuItem?: string;
    menuItemImg?: string;
    sousmenus?: ISousmenu[];
}

export class Menu implements IMenu {
    constructor(public id?: number, public menuItem?: string, public menuItemImg?: string, public sousmenus?: ISousmenu[]) {}
}
