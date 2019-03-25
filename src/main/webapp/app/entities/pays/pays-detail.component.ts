import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPays } from 'app/shared/model/pays.model';

@Component({
    selector: 'jhi-pays-detail',
    templateUrl: './pays-detail.component.html'
})
export class PaysDetailComponent implements OnInit {
    pays: IPays;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pays }) => {
            this.pays = pays;
        });
    }

    previousState() {
        window.history.back();
    }
}
