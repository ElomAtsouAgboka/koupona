import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'pays',
                loadChildren: './pays/pays.module#KouponaPaysModule'
            },
            {
                path: 'ville',
                loadChildren: './ville/ville.module#KouponaVilleModule'
            },
            {
                path: 'ville',
                loadChildren: './ville/ville.module#KouponaVilleModule'
            },
            {
                path: 'quartier',
                loadChildren: './quartier/quartier.module#KouponaQuartierModule'
            },
            {
                path: 'topcategorie',
                loadChildren: './topcategorie/topcategorie.module#KouponaTopcategorieModule'
            },
            {
                path: 'categorie',
                loadChildren: './categorie/categorie.module#KouponaCategorieModule'
            },
            {
                path: 'categorie',
                loadChildren: './categorie/categorie.module#KouponaCategorieModule'
            },
            {
                path: 'souscategorie',
                loadChildren: './souscategorie/souscategorie.module#KouponaSouscategorieModule'
            },
            {
                path: 'menu',
                loadChildren: './menu/menu.module#KouponaMenuModule'
            },
            {
                path: 'sousmenu',
                loadChildren: './sousmenu/sousmenu.module#KouponaSousmenuModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponaEntityModule {}
