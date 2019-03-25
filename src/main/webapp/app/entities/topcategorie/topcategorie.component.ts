import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITopcategorie } from 'app/shared/model/topcategorie.model';
import { AccountService } from 'app/core';
import { TopcategorieService } from './topcategorie.service';

@Component({
    selector: 'jhi-topcategorie',
    templateUrl: './topcategorie.component.html'
})
export class TopcategorieComponent implements OnInit, OnDestroy {
    topcategories: ITopcategorie[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected topcategorieService: TopcategorieService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.topcategorieService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ITopcategorie[]>) => res.ok),
                    map((res: HttpResponse<ITopcategorie[]>) => res.body)
                )
                .subscribe((res: ITopcategorie[]) => (this.topcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.topcategorieService
            .query()
            .pipe(
                filter((res: HttpResponse<ITopcategorie[]>) => res.ok),
                map((res: HttpResponse<ITopcategorie[]>) => res.body)
            )
            .subscribe(
                (res: ITopcategorie[]) => {
                    this.topcategories = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTopcategories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITopcategorie) {
        return item.id;
    }

    registerChangeInTopcategories() {
        this.eventSubscriber = this.eventManager.subscribe('topcategorieListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
