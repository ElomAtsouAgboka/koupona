import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Topcategorie } from 'app/shared/model/topcategorie.model';
import { TopcategorieService } from './topcategorie.service';
import { TopcategorieComponent } from './topcategorie.component';
import { TopcategorieDetailComponent } from './topcategorie-detail.component';
import { TopcategorieUpdateComponent } from './topcategorie-update.component';
import { TopcategorieDeletePopupComponent } from './topcategorie-delete-dialog.component';
import { ITopcategorie } from 'app/shared/model/topcategorie.model';

@Injectable({ providedIn: 'root' })
export class TopcategorieResolve implements Resolve<ITopcategorie> {
    constructor(private service: TopcategorieService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITopcategorie> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Topcategorie>) => response.ok),
                map((topcategorie: HttpResponse<Topcategorie>) => topcategorie.body)
            );
        }
        return of(new Topcategorie());
    }
}

export const topcategorieRoute: Routes = [
    {
        path: '',
        component: TopcategorieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.topcategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TopcategorieDetailComponent,
        resolve: {
            topcategorie: TopcategorieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.topcategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TopcategorieUpdateComponent,
        resolve: {
            topcategorie: TopcategorieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.topcategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TopcategorieUpdateComponent,
        resolve: {
            topcategorie: TopcategorieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.topcategorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const topcategoriePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TopcategorieDeletePopupComponent,
        resolve: {
            topcategorie: TopcategorieResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kouponaApp.topcategorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
