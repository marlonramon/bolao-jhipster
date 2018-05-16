/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BolaoTestModule } from '../../../test.module';
import { CampeonatoComponent } from '../../../../../../main/webapp/app/entities/campeonato/campeonato.component';
import { CampeonatoService } from '../../../../../../main/webapp/app/entities/campeonato/campeonato.service';
import { Campeonato } from '../../../../../../main/webapp/app/entities/campeonato/campeonato.model';

describe('Component Tests', () => {

    describe('Campeonato Management Component', () => {
        let comp: CampeonatoComponent;
        let fixture: ComponentFixture<CampeonatoComponent>;
        let service: CampeonatoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [CampeonatoComponent],
                providers: [
                    CampeonatoService
                ]
            })
            .overrideTemplate(CampeonatoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CampeonatoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CampeonatoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Campeonato(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.campeonatoes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
