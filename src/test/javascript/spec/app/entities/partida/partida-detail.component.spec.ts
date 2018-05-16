/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BolaoTestModule } from '../../../test.module';
import { PartidaDetailComponent } from '../../../../../../main/webapp/app/entities/partida/partida-detail.component';
import { PartidaService } from '../../../../../../main/webapp/app/entities/partida/partida.service';
import { Partida } from '../../../../../../main/webapp/app/entities/partida/partida.model';

describe('Component Tests', () => {

    describe('Partida Management Detail Component', () => {
        let comp: PartidaDetailComponent;
        let fixture: ComponentFixture<PartidaDetailComponent>;
        let service: PartidaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [PartidaDetailComponent],
                providers: [
                    PartidaService
                ]
            })
            .overrideTemplate(PartidaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartidaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartidaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Partida(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.partida).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
