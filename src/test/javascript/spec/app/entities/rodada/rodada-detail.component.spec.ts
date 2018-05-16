/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BolaoTestModule } from '../../../test.module';
import { RodadaDetailComponent } from '../../../../../../main/webapp/app/entities/rodada/rodada-detail.component';
import { RodadaService } from '../../../../../../main/webapp/app/entities/rodada/rodada.service';
import { Rodada } from '../../../../../../main/webapp/app/entities/rodada/rodada.model';

describe('Component Tests', () => {

    describe('Rodada Management Detail Component', () => {
        let comp: RodadaDetailComponent;
        let fixture: ComponentFixture<RodadaDetailComponent>;
        let service: RodadaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [RodadaDetailComponent],
                providers: [
                    RodadaService
                ]
            })
            .overrideTemplate(RodadaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RodadaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RodadaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Rodada(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.rodada).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
