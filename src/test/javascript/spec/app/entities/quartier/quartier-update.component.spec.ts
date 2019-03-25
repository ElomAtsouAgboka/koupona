/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KouponaTestModule } from '../../../test.module';
import { QuartierUpdateComponent } from 'app/entities/quartier/quartier-update.component';
import { QuartierService } from 'app/entities/quartier/quartier.service';
import { Quartier } from 'app/shared/model/quartier.model';

describe('Component Tests', () => {
    describe('Quartier Management Update Component', () => {
        let comp: QuartierUpdateComponent;
        let fixture: ComponentFixture<QuartierUpdateComponent>;
        let service: QuartierService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [QuartierUpdateComponent]
            })
                .overrideTemplate(QuartierUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QuartierUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuartierService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Quartier(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.quartier = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Quartier();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.quartier = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
