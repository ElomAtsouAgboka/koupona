/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KouponaTestModule } from '../../../test.module';
import { TopcategorieComponent } from 'app/entities/topcategorie/topcategorie.component';
import { TopcategorieService } from 'app/entities/topcategorie/topcategorie.service';
import { Topcategorie } from 'app/shared/model/topcategorie.model';

describe('Component Tests', () => {
    describe('Topcategorie Management Component', () => {
        let comp: TopcategorieComponent;
        let fixture: ComponentFixture<TopcategorieComponent>;
        let service: TopcategorieService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KouponaTestModule],
                declarations: [TopcategorieComponent],
                providers: []
            })
                .overrideTemplate(TopcategorieComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TopcategorieComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TopcategorieService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Topcategorie(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.topcategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
