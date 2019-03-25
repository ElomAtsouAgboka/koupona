import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { KouponaSharedModule } from 'app/shared';
import {
    TopcategorieComponent,
    TopcategorieDetailComponent,
    TopcategorieUpdateComponent,
    TopcategorieDeletePopupComponent,
    TopcategorieDeleteDialogComponent,
    topcategorieRoute,
    topcategoriePopupRoute
} from './';

const ENTITY_STATES = [...topcategorieRoute, ...topcategoriePopupRoute];

@NgModule({
    imports: [KouponaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TopcategorieComponent,
        TopcategorieDetailComponent,
        TopcategorieUpdateComponent,
        TopcategorieDeleteDialogComponent,
        TopcategorieDeletePopupComponent
    ],
    entryComponents: [
        TopcategorieComponent,
        TopcategorieUpdateComponent,
        TopcategorieDeleteDialogComponent,
        TopcategorieDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KouponaTopcategorieModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
