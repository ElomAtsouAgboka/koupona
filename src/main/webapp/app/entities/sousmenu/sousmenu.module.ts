import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponaSharedModule } from 'app/shared';
import {
    SousmenuComponent,
    SousmenuDetailComponent,
    SousmenuUpdateComponent,
    SousmenuDeletePopupComponent,
    SousmenuDeleteDialogComponent,
    sousmenuRoute,
    sousmenuPopupRoute
} from './';

const ENTITY_STATES = [...sousmenuRoute, ...sousmenuPopupRoute];

@NgModule({
    imports: [KouponaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SousmenuComponent,
        SousmenuDetailComponent,
        SousmenuUpdateComponent,
        SousmenuDeleteDialogComponent,
        SousmenuDeletePopupComponent
    ],
    entryComponents: [SousmenuComponent, SousmenuUpdateComponent, SousmenuDeleteDialogComponent, SousmenuDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponaSousmenuModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
