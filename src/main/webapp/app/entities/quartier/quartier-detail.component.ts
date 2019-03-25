import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuartier } from 'app/shared/model/quartier.model';

@Component({
    selector: 'jhi-quartier-detail',
    templateUrl: './quartier-detail.component.html'
})
export class QuartierDetailComponent implements OnInit {
    quartier: IQuartier;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quartier }) => {
            this.quartier = quartier;
        });
    }

    previousState() {
        window.history.back();
    }
}
