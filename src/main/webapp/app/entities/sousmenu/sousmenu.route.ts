import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sousmenu } from 'app/shared/model/sousmenu.model';
import { SousmenuService } from './sousmenu.service';
import { SousmenuComponent } from './sousmenu.component';
import { SousmenuDetailComponent } from './sousmenu-detail.component';
import { SousmenuUpdateComponent } from './sousmenu-update.component';
import { SousmenuDeletePopupComponent } from './sousmenu-delete-dialog.component';
import { ISousmenu } from 'app/shared/model/sousmenu.model';

@Injectable({ providedIn: 'root' })
export class SousmenuResolve implements Resolve<ISousmenu> {
    constructor(private service: SousmenuService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISousmenu> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Sousmenu>) => response.ok),
                map((sousmenu: HttpResponse<Sousmenu>) => sousmenu.body)
            );
        }
        return of(new Sousmenu());
    }
}

export const sousmenuRoute: Routes = [
    {
        path: '',
        component: SousmenuComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'kouponaApp.sousmenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SousmenuDetailComponent,
        resolve: {
            sousmenu: SousmenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.sousmenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SousmenuUpdateComponent,
        resolve: {
            sousmenu: SousmenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.sousmenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SousmenuUpdateComponent,
        resolve: {
            sousmenu: SousmenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.sousmenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sousmenuPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SousmenuDeletePopupComponent,
        resolve: {
            sousmenu: SousmenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.sousmenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
