import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPays } from 'app/shared/model/pays.model';
import { PaysService } from './pays.service';

@Component({
    selector: 'jhi-pays-delete-dialog',
    templateUrl: './pays-delete-dialog.component.html'
})
export class PaysDeleteDialogComponent {
    pays: IPays;

    constructor(protected paysService: PaysService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paysService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'paysListModification',
                content: 'Deleted an pays'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pays-delete-popup',
    template: ''
})
export class PaysDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pays }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PaysDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pays = pays;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/pays', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/pays', { outlets: { popup: null } }]);
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
