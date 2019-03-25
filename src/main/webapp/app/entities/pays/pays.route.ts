import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pays } from 'app/shared/model/pays.model';
import { PaysService } from './pays.service';
import { PaysComponent } from './pays.component';
import { PaysDetailComponent } from './pays-detail.component';
import { PaysUpdateComponent } from './pays-update.component';
import { PaysDeletePopupComponent } from './pays-delete-dialog.component';
import { IPays } from 'app/shared/model/pays.model';

@Injectable({ providedIn: 'root' })
export class PaysResolve implements Resolve<IPays> {
    constructor(private service: PaysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPays> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Pays>) => response.ok),
                map((pays: HttpResponse<Pays>) => pays.body)
            );
        }
        return of(new Pays());
    }
}

export const paysRoute: Routes = [
    {
        path: '',
        component: PaysComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'kouponaApp.pays.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PaysDetailComponent,
        resolve: {
            pays: PaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.pays.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PaysUpdateComponent,
        resolve: {
            pays: PaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.pays.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PaysUpdateComponent,
        resolve: {
            pays: PaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.pays.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paysPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PaysDeletePopupComponent,
        resolve: {
            pays: PaysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.pays.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
