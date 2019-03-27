import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Souscategorie } from 'app/shared/model/souscategorie.model';
import { SouscategorieService } from './souscategorie.service';
import { SouscategorieComponent } from './souscategorie.component';
import { SouscategorieDetailComponent } from './souscategorie-detail.component';
import { SouscategorieUpdateComponent } from './souscategorie-update.component';
import { SouscategorieDeletePopupComponent } from './souscategorie-delete-dialog.component';
import { ISouscategorie } from 'app/shared/model/souscategorie.model';

@Injectable({ providedIn: 'root' })
export class SouscategorieResolve implements Resolve<ISouscategorie> {
    constructor(private service: SouscategorieService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISouscategorie> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Souscategorie>) => response.ok),
                map((souscategorie: HttpResponse<Souscategorie>) => souscategorie.body)
            );
        }
        return of(new Souscategorie());
    }
}

export const souscategorieRoute: Routes = [
    {
        path: '',
        component: SouscategorieComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'kouponaApp.souscategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SouscategorieDetailComponent,
        resolve: {
            souscategorie: SouscategorieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.souscategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SouscategorieUpdateComponent,
        resolve: {
            souscategorie: SouscategorieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.souscategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SouscategorieUpdateComponent,
        resolve: {
            souscategorie: SouscategorieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.souscategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const souscategoriePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SouscategorieDeletePopupComponent,
        resolve: {
            souscategorie: SouscategorieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.souscategorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
