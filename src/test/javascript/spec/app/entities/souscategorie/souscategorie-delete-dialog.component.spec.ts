/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KouponaTestModule } from '../../../test.module';
import { SouscategorieDeleteDialogComponent } from 'app/entities/souscategorie/souscategorie-delete-dialog.component';
import { SouscategorieService } from 'app/entities/souscategorie/souscategorie.service';

describe('Component Tests', () => {
    describe('Souscategorie Management Delete Component', () => {
        let comp: SouscategorieDeleteDialogComponent;
        let fixture: ComponentFixture<SouscategorieDeleteDialogComponent>;
        let service: SouscategorieService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [SouscategorieDeleteDialogComponent]
            })
                .overrideTemplate(SouscategorieDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SouscategorieDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SouscategorieService);
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
