/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KouponaTestModule } from '../../../test.module';
import { PaysDetailComponent } from 'app/entities/pays/pays-detail.component';
import { Pays } from 'app/shared/model/pays.model';

describe('Component Tests', () => {
    describe('Pays Management Detail Component', () => {
        let comp: PaysDetailComponent;
        let fixture: ComponentFixture<PaysDetailComponent>;
        const route = ({ data: of({ pays: new Pays(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [PaysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PaysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PaysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pays).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
