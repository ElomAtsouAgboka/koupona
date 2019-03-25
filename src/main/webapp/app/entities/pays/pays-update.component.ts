import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IPays } from 'app/shared/model/pays.model';
import { PaysService } from './pays.service';

@Component({
    selector: 'jhi-pays-update',
    templateUrl: './pays-update.component.html'
})
export class PaysUpdateComponent implements OnInit {
    pays: IPays;
    isSaving: boolean;

    constructor(protected paysService: PaysService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pays }) => {
            this.pays = pays;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pays.id !== undefined) {
            this.subscribeToSaveResponse(this.paysService.update(this.pays));
        } else {
            this.subscribeToSaveResponse(this.paysService.create(this.pays));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPays>>) {
        result.subscribe((res: HttpResponse<IPays>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
