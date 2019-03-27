import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISouscategorie } from 'app/shared/model/souscategorie.model';
import { SouscategorieService } from './souscategorie.service';

@Component({
    selector: 'jhi-souscategorie-delete-dialog',
    templateUrl: './souscategorie-delete-dialog.component.html'
})
export class SouscategorieDeleteDialogComponent {
    souscategorie: ISouscategorie;

    constructor(
        protected souscategorieService: SouscategorieService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.souscategorieService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'souscategorieListModification',
                content: 'Deleted an souscategorie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-souscategorie-delete-popup',
    template: ''
})
export class SouscategorieDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ souscategorie }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SouscategorieDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.souscategorie = souscategorie;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/souscategorie', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/souscategorie', { outlets: { popup: null } }]);
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
