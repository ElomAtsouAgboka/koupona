import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITopcategorie } from 'app/shared/model/topcategorie.model';

@Component({
    selector: 'jhi-topcategorie-detail',
    templateUrl: './topcategorie-detail.component.html'
})
export class TopcategorieDetailComponent implements OnInit {
    topcategorie: ITopcategorie;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ topcategorie }) => {
            this.topcategorie = topcategorie;
        });
    }

    previousState() {
        window.history.back();
    }
}
