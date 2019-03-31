import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISousmenu } from 'app/shared/model/sousmenu.model';
import { SousmenuService } from './sousmenu.service';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from 'app/entities/menu';

@Component({
    selector: 'jhi-sousmenu-update',
    templateUrl: './sousmenu-update.component.html'
})
export class SousmenuUpdateComponent implements OnInit {
    sousmenu: ISousmenu;
    isSaving: boolean;

    menus: IMenu[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected sousmenuService: SousmenuService,
        protected menuService: MenuService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sousmenu }) => {
            this.sousmenu = sousmenu;
        });
        this.menuService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMenu[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMenu[]>) => response.body)
            )
            .subscribe((res: IMenu[]) => (this.menus = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sousmenu.id !== undefined) {
            this.subscribeToSaveResponse(this.sousmenuService.update(this.sousmenu));
        } else {
            this.subscribeToSaveResponse(this.sousmenuService.create(this.sousmenu));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISousmenu>>) {
        result.subscribe((res: HttpResponse<ISousmenu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMenuById(index: number, item: IMenu) {
        return item.id;
    }
}
