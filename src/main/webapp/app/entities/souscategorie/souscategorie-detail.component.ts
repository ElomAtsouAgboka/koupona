import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISouscategorie } from 'app/shared/model/souscategorie.model';

@Component({
    selector: 'jhi-souscategorie-detail',
    templateUrl: './souscategorie-detail.component.html'
})
export class SouscategorieDetailComponent implements OnInit {
    souscategorie: ISouscategorie;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ souscategorie }) => {
            this.souscategorie = souscategorie;
        });
    }

    previousState() {
        window.history.back();
    }
}
