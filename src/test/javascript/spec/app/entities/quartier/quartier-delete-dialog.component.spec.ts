/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KouponaTestModule } from '../../../test.module';
import { QuartierDeleteDialogComponent } from 'app/entities/quartier/quartier-delete-dialog.component';
import { QuartierService } from 'app/entities/quartier/quartier.service';

describe('Component Tests', () => {
    describe('Quartier Management Delete Component', () => {
        let comp: QuartierDeleteDialogComponent;
        let fixture: ComponentFixture<QuartierDeleteDialogComponent>;
        let service: QuartierService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [QuartierDeleteDialogComponent]
            })
                .overrideTemplate(QuartierDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuartierDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuartierService);
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
