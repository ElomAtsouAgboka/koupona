/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KouponaTestModule } from '../../../test.module';
import { TopcategorieUpdateComponent } from 'app/entities/topcategorie/topcategorie-update.component';
import { TopcategorieService } from 'app/entities/topcategorie/topcategorie.service';
import { Topcategorie } from 'app/shared/model/topcategorie.model';

describe('Component Tests', () => {
    describe('Topcategorie Management Update Component', () => {
        let comp: TopcategorieUpdateComponent;
        let fixture: ComponentFixture<TopcategorieUpdateComponent>;
        let service: TopcategorieService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [TopcategorieUpdateComponent]
            })
                .overrideTemplate(TopcategorieUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TopcategorieUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TopcategorieService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Topcategorie(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.topcategorie = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Topcategorie();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.topcategorie = entity;
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
