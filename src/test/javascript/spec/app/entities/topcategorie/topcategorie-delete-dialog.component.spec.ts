/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KouponaTestModule } from '../../../test.module';
import { TopcategorieDeleteDialogComponent } from 'app/entities/topcategorie/topcategorie-delete-dialog.component';
import { TopcategorieService } from 'app/entities/topcategorie/topcategorie.service';

describe('Component Tests', () => {
    describe('Topcategorie Management Delete Component', () => {
        let comp: TopcategorieDeleteDialogComponent;
        let fixture: ComponentFixture<TopcategorieDeleteDialogComponent>;
        let service: TopcategorieService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [TopcategorieDeleteDialogComponent]
            })
                .overrideTemplate(TopcategorieDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TopcategorieDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TopcategorieService);
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
