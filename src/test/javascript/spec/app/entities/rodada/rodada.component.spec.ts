/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BolaoTestModule } from '../../../test.module';
import { RodadaComponent } from '../../../../../../main/webapp/app/entities/rodada/rodada.component';
import { RodadaService } from '../../../../../../main/webapp/app/entities/rodada/rodada.service';
import { Rodada } from '../../../../../../main/webapp/app/entities/rodada/rodada.model';

describe('Component Tests', () => {

    describe('Rodada Management Component', () => {
        let comp: RodadaComponent;
        let fixture: ComponentFixture<RodadaComponent>;
        let service: RodadaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [RodadaComponent],
                providers: [
                    RodadaService
                ]
            })
            .overrideTemplate(RodadaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RodadaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RodadaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Rodada(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.rodadas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
