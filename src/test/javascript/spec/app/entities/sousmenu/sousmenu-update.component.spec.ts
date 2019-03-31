/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KouponaTestModule } from '../../../test.module';
import { SousmenuUpdateComponent } from 'app/entities/sousmenu/sousmenu-update.component';
import { SousmenuService } from 'app/entities/sousmenu/sousmenu.service';
import { Sousmenu } from 'app/shared/model/sousmenu.model';

describe('Component Tests', () => {
    describe('Sousmenu Management Update Component', () => {
        let comp: SousmenuUpdateComponent;
        let fixture: ComponentFixture<SousmenuUpdateComponent>;
        let service: SousmenuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [SousmenuUpdateComponent]
            })
                .overrideTemplate(SousmenuUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SousmenuUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SousmenuService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Sousmenu(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.sousmenu = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Sousmenu();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.sousmenu = entity;
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
