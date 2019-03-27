import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISousmenu } from 'app/shared/model/sousmenu.model';

@Component({
    selector: 'jhi-sousmenu-detail',
    templateUrl: './sousmenu-detail.component.html'
})
export class SousmenuDetailComponent implements OnInit {
    sousmenu: ISousmenu;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sousmenu }) => {
            this.sousmenu = sousmenu;
        });
    }

    previousState() {
        window.history.back();
    }
}
