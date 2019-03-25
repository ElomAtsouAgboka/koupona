/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KouponaTestModule } from '../../../test.module';
import { PaysDeleteDialogComponent } from 'app/entities/pays/pays-delete-dialog.component';
import { PaysService } from 'app/entities/pays/pays.service';

describe('Component Tests', () => {
    describe('Pays Management Delete Component', () => {
        let comp: PaysDeleteDialogComponent;
        let fixture: ComponentFixture<PaysDeleteDialogComponent>;
        let service: PaysService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [PaysDeleteDialogComponent]
            })
                .overrideTemplate(PaysDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaysDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaysService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
