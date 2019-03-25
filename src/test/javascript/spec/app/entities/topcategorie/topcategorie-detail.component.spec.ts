/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KouponaTestModule } from '../../../test.module';
import { TopcategorieDetailComponent } from 'app/entities/topcategorie/topcategorie-detail.component';
import { Topcategorie } from 'app/shared/model/topcategorie.model';

describe('Component Tests', () => {
    describe('Topcategorie Management Detail Component', () => {
        let comp: TopcategorieDetailComponent;
        let fixture: ComponentFixture<TopcategorieDetailComponent>;
        const route = ({ data: of({ topcategorie: new Topcategorie(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [TopcategorieDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TopcategorieDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TopcategorieDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.topcategorie).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
