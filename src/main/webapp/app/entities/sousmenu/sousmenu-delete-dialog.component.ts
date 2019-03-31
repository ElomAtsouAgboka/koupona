import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISousmenu } from 'app/shared/model/sousmenu.model';
import { SousmenuService } from './sousmenu.service';

@Component({
    selector: 'jhi-sousmenu-delete-dialog',
    templateUrl: './sousmenu-delete-dialog.component.html'
})
export class SousmenuDeleteDialogComponent {
    sousmenu: ISousmenu;

    constructor(protected sousmenuService: SousmenuService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sousmenuService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sousmenuListModification',
                content: 'Deleted an sousmenu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sousmenu-delete-popup',
    template: ''
})
export class SousmenuDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sousmenu }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SousmenuDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sousmenu = sousmenu;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/sousmenu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/sousmenu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
