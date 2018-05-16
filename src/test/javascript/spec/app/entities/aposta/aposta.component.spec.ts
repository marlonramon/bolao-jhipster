/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BolaoTestModule } from '../../../test.module';
import { ApostaComponent } from '../../../../../../main/webapp/app/entities/aposta/aposta.component';
import { ApostaService } from '../../../../../../main/webapp/app/entities/aposta/aposta.service';
import { Aposta } from '../../../../../../main/webapp/app/entities/aposta/aposta.model';

describe('Component Tests', () => {

    describe('Aposta Management Component', () => {
        let comp: ApostaComponent;
        let fixture: ComponentFixture<ApostaComponent>;
        let service: ApostaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [ApostaComponent],
                providers: [
                    ApostaService
                ]
            })
            .overrideTemplate(ApostaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApostaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApostaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Aposta(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.apostas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
