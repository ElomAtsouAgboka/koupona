import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITopcategorie } from 'app/shared/model/topcategorie.model';
import { TopcategorieService } from './topcategorie.service';

@Component({
    selector: 'jhi-topcategorie-delete-dialog',
    templateUrl: './topcategorie-delete-dialog.component.html'
})
export class TopcategorieDeleteDialogComponent {
    topcategorie: ITopcategorie;

    constructor(
        protected topcategorieService: TopcategorieService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.topcategorieService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'topcategorieListModification',
                content: 'Deleted an topcategorie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-topcategorie-delete-popup',
    template: ''
})
export class TopcategorieDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ topcategorie }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TopcategorieDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.topcategorie = topcategorie;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/topcategorie', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/topcategorie', { outlets: { popup: null } }]);
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
