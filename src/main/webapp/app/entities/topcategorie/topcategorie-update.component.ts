import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITopcategorie } from 'app/shared/model/topcategorie.model';
import { TopcategorieService } from './topcategorie.service';

@Component({
    selector: 'jhi-topcategorie-update',
    templateUrl: './topcategorie-update.component.html'
})
export class TopcategorieUpdateComponent implements OnInit {
    topcategorie: ITopcategorie;
    isSaving: boolean;

    constructor(protected topcategorieService: TopcategorieService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ topcategorie }) => {
            this.topcategorie = topcategorie;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.topcategorie.id !== undefined) {
            this.subscribeToSaveResponse(this.topcategorieService.update(this.topcategorie));
        } else {
            this.subscribeToSaveResponse(this.topcategorieService.create(this.topcategorie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopcategorie>>) {
        result.subscribe((res: HttpResponse<ITopcategorie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
