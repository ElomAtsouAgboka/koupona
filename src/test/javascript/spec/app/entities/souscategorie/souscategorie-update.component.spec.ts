/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KouponaTestModule } from '../../../test.module';
import { SouscategorieUpdateComponent } from 'app/entities/souscategorie/souscategorie-update.component';
import { SouscategorieService } from 'app/entities/souscategorie/souscategorie.service';
import { Souscategorie } from 'app/shared/model/souscategorie.model';

describe('Component Tests', () => {
    describe('Souscategorie Management Update Component', () => {
        let comp: SouscategorieUpdateComponent;
        let fixture: ComponentFixture<SouscategorieUpdateComponent>;
        let service: SouscategorieService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [SouscategorieUpdateComponent]
            })
                .overrideTemplate(SouscategorieUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SouscategorieUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SouscategorieService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Souscategorie(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.souscategorie = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Souscategorie();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.souscategorie = entity;
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
