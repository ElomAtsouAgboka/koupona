import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponaSharedModule } from 'app/shared';
import {
    SouscategorieComponent,
    SouscategorieDetailComponent,
    SouscategorieUpdateComponent,
    SouscategorieDeletePopupComponent,
    SouscategorieDeleteDialogComponent,
    souscategorieRoute,
    souscategoriePopupRoute
} from './';

const ENTITY_STATES = [...souscategorieRoute, ...souscategoriePopupRoute];

@NgModule({
    imports: [KouponaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SouscategorieComponent,
        SouscategorieDetailComponent,
        SouscategorieUpdateComponent,
        SouscategorieDeleteDialogComponent,
        SouscategorieDeletePopupComponent
    ],
    entryComponents: [
        SouscategorieComponent,
        SouscategorieUpdateComponent,
        SouscategorieDeleteDialogComponent,
        SouscategorieDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponaSouscategorieModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
