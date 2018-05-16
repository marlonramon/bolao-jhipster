/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BolaoTestModule } from '../../../test.module';
import { CampeonatoDetailComponent } from '../../../../../../main/webapp/app/entities/campeonato/campeonato-detail.component';
import { CampeonatoService } from '../../../../../../main/webapp/app/entities/campeonato/campeonato.service';
import { Campeonato } from '../../../../../../main/webapp/app/entities/campeonato/campeonato.model';

describe('Component Tests', () => {

    describe('Campeonato Management Detail Component', () => {
        let comp: CampeonatoDetailComponent;
        let fixture: ComponentFixture<CampeonatoDetailComponent>;
        let service: CampeonatoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [CampeonatoDetailComponent],
                providers: [
                    CampeonatoService
                ]
            })
            .overrideTemplate(CampeonatoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CampeonatoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CampeonatoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Campeonato(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.campeonato).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
