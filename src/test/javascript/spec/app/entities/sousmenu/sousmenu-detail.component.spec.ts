/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KouponaTestModule } from '../../../test.module';
import { SousmenuDetailComponent } from 'app/entities/sousmenu/sousmenu-detail.component';
import { Sousmenu } from 'app/shared/model/sousmenu.model';

describe('Component Tests', () => {
    describe('Sousmenu Management Detail Component', () => {
        let comp: SousmenuDetailComponent;
        let fixture: ComponentFixture<SousmenuDetailComponent>;
        const route = ({ data: of({ sousmenu: new Sousmenu(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [SousmenuDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SousmenuDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SousmenuDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sousmenu).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
