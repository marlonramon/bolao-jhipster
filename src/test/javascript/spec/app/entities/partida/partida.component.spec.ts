/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BolaoTestModule } from '../../../test.module';
import { PartidaComponent } from '../../../../../../main/webapp/app/entities/partida/partida.component';
import { PartidaService } from '../../../../../../main/webapp/app/entities/partida/partida.service';
import { Partida } from '../../../../../../main/webapp/app/entities/partida/partida.model';

describe('Component Tests', () => {

    describe('Partida Management Component', () => {
        let comp: PartidaComponent;
        let fixture: ComponentFixture<PartidaComponent>;
        let service: PartidaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [PartidaComponent],
                providers: [
                    PartidaService
                ]
            })
            .overrideTemplate(PartidaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartidaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartidaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Partida(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.partidas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
