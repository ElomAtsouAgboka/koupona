/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KouponaTestModule } from '../../../test.module';
import { SouscategorieDetailComponent } from 'app/entities/souscategorie/souscategorie-detail.component';
import { Souscategorie } from 'app/shared/model/souscategorie.model';

describe('Component Tests', () => {
    describe('Souscategorie Management Detail Component', () => {
        let comp: SouscategorieDetailComponent;
        let fixture: ComponentFixture<SouscategorieDetailComponent>;
        const route = ({ data: of({ souscategorie: new Souscategorie(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [SouscategorieDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SouscategorieDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SouscategorieDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.souscategorie).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
