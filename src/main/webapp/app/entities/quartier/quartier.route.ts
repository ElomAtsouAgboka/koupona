import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Quartier } from 'app/shared/model/quartier.model';
import { QuartierService } from './quartier.service';
import { QuartierComponent } from './quartier.component';
import { QuartierDetailComponent } from './quartier-detail.component';
import { QuartierUpdateComponent } from './quartier-update.component';
import { QuartierDeletePopupComponent } from './quartier-delete-dialog.component';
import { IQuartier } from 'app/shared/model/quartier.model';

@Injectable({ providedIn: 'root' })
export class QuartierResolve implements Resolve<IQuartier> {
    constructor(private service: QuartierService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuartier> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Quartier>) => response.ok),
                map((quartier: HttpResponse<Quartier>) => quartier.body)
            );
        }
        return of(new Quartier());
    }
}

export const quartierRoute: Routes = [
    {
        path: '',
        component: QuartierComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'kouponaApp.quartier.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: QuartierDetailComponent,
        resolve: {
            quartier: QuartierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.quartier.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: QuartierUpdateComponent,
        resolve: {
            quartier: QuartierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.quartier.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: QuartierUpdateComponent,
        resolve: {
            quartier: QuartierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.quartier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quartierPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: QuartierDeletePopupComponent,
        resolve: {
            quartier: QuartierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.quartier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
