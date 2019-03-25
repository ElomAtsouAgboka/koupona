import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IQuartier } from 'app/shared/model/quartier.model';
import { QuartierService } from './quartier.service';
import { IVille } from 'app/shared/model/ville.model';
import { VilleService } from 'app/entities/ville';

@Component({
    selector: 'jhi-quartier-update',
    templateUrl: './quartier-update.component.html'
})
export class QuartierUpdateComponent implements OnInit {
    quartier: IQuartier;
    isSaving: boolean;

    villes: IVille[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected quartierService: QuartierService,
        protected villeService: VilleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quartier }) => {
            this.quartier = quartier;
        });
        this.villeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVille[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVille[]>) => response.body)
            )
            .subscribe((res: IVille[]) => (this.villes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.quartier.id !== undefined) {
            this.subscribeToSaveResponse(this.quartierService.update(this.quartier));
        } else {
            this.subscribeToSaveResponse(this.quartierService.create(this.quartier));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuartier>>) {
        result.subscribe((res: HttpResponse<IQuartier>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVilleById(index: number, item: IVille) {
        return item.id;
    }
}
