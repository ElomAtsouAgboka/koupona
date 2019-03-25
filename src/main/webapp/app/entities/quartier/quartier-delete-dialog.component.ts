import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuartier } from 'app/shared/model/quartier.model';
import { QuartierService } from './quartier.service';

@Component({
    selector: 'jhi-quartier-delete-dialog',
    templateUrl: './quartier-delete-dialog.component.html'
})
export class QuartierDeleteDialogComponent {
    quartier: IQuartier;

    constructor(protected quartierService: QuartierService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.quartierService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'quartierListModification',
                content: 'Deleted an quartier'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-quartier-delete-popup',
    template: ''
})
export class QuartierDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quartier }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuartierDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.quartier = quartier;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/quartier', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/quartier', { outlets: { popup: null } }]);
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
