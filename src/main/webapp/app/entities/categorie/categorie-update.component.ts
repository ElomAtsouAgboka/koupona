import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICategorie } from 'app/shared/model/categorie.model';
import { CategorieService } from './categorie.service';
import { ITopcategorie } from 'app/shared/model/topcategorie.model';
import { TopcategorieService } from 'app/entities/topcategorie';

@Component({
    selector: 'jhi-categorie-update',
    templateUrl: './categorie-update.component.html'
})
export class CategorieUpdateComponent implements OnInit {
    categorie: ICategorie;
    isSaving: boolean;

    topcategories: ITopcategorie[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected categorieService: CategorieService,
        protected topcategorieService: TopcategorieService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ categorie }) => {
            this.categorie = categorie;
        });
        this.topcategorieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITopcategorie[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITopcategorie[]>) => response.body)
            )
            .subscribe((res: ITopcategorie[]) => (this.topcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.categorie.id !== undefined) {
            this.subscribeToSaveResponse(this.categorieService.update(this.categorie));
        } else {
            this.subscribeToSaveResponse(this.categorieService.create(this.categorie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorie>>) {
        result.subscribe((res: HttpResponse<ICategorie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTopcategorieById(index: number, item: ITopcategorie) {
        return item.id;
    }
}
