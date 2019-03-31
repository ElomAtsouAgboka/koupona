import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';

@Component({
    selector: 'jhi-menu-update',
    templateUrl: './menu-update.component.html'
})
export class MenuUpdateComponent implements OnInit {
    menu: IMenu;
    isSaving: boolean;

    constructor(protected menuService: MenuService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ menu }) => {
            this.menu = menu;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.menu.id !== undefined) {
            this.subscribeToSaveResponse(this.menuService.update(this.menu));
        } else {
            this.subscribeToSaveResponse(this.menuService.create(this.menu));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenu>>) {
        result.subscribe((res: HttpResponse<IMenu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
