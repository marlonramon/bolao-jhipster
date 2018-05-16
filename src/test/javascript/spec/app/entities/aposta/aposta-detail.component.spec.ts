/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BolaoTestModule } from '../../../test.module';
import { ApostaDetailComponent } from '../../../../../../main/webapp/app/entities/aposta/aposta-detail.component';
import { ApostaService } from '../../../../../../main/webapp/app/entities/aposta/aposta.service';
import { Aposta } from '../../../../../../main/webapp/app/entities/aposta/aposta.model';

describe('Component Tests', () => {

    describe('Aposta Management Detail Component', () => {
        let comp: ApostaDetailComponent;
        let fixture: ComponentFixture<ApostaDetailComponent>;
        let service: ApostaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [ApostaDetailComponent],
                providers: [
                    ApostaService
                ]
            })
            .overrideTemplate(ApostaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ApostaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApostaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Aposta(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.aposta).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
