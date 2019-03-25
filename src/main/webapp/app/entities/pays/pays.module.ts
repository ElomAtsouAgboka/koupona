import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponaSharedModule } from 'app/shared';
import {
    PaysComponent,
    PaysDetailComponent,
    PaysUpdateComponent,
    PaysDeletePopupComponent,
    PaysDeleteDialogComponent,
    paysRoute,
    paysPopupRoute
} from './';

const ENTITY_STATES = [...paysRoute, ...paysPopupRoute];

@NgModule({
    imports: [KouponaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [PaysComponent, PaysDetailComponent, PaysUpdateComponent, PaysDeleteDialogComponent, PaysDeletePopupComponent],
    entryComponents: [PaysComponent, PaysUpdateComponent, PaysDeleteDialogComponent, PaysDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponaPaysModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
