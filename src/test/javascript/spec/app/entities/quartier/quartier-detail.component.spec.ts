/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KouponaTestModule } from '../../../test.module';
import { QuartierDetailComponent } from 'app/entities/quartier/quartier-detail.component';
import { Quartier } from 'app/shared/model/quartier.model';

describe('Component Tests', () => {
    describe('Quartier Management Detail Component', () => {
        let comp: QuartierDetailComponent;
        let fixture: ComponentFixture<QuartierDetailComponent>;
        const route = ({ data: of({ quartier: new Quartier(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [QuartierDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QuartierDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuartierDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.quartier).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
