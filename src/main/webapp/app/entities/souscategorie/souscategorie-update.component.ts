import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISouscategorie } from 'app/shared/model/souscategorie.model';
import { SouscategorieService } from './souscategorie.service';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from 'app/entities/categorie';

@Component({
    selector: 'jhi-souscategorie-update',
    templateUrl: './souscategorie-update.component.html'
})
export class SouscategorieUpdateComponent implements OnInit {
    souscategorie: ISouscategorie;
    isSaving: boolean;

    categories: ICategorie[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected souscategorieService: SouscategorieService,
        protected categorieService: CategorieService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ souscategorie }) => {
            this.souscategorie = souscategorie;
        });
        this.categorieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategorie[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategorie[]>) => response.body)
            )
            .subscribe((res: ICategorie[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.souscategorie.id !== undefined) {
            this.subscribeToSaveResponse(this.souscategorieService.update(this.souscategorie));
        } else {
            this.subscribeToSaveResponse(this.souscategorieService.create(this.souscategorie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISouscategorie>>) {
        result.subscribe((res: HttpResponse<ISouscategorie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCategorieById(index: number, item: ICategorie) {
        return item.id;
    }
}
