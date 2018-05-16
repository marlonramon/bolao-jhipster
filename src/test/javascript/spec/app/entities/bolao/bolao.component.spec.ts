/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BolaoTestModule } from '../../../test.module';
import { BolaoComponent } from '../../../../../../main/webapp/app/entities/bolao/bolao.component';
import { BolaoService } from '../../../../../../main/webapp/app/entities/bolao/bolao.service';
import { Bolao } from '../../../../../../main/webapp/app/entities/bolao/bolao.model';

describe('Component Tests', () => {

    describe('Bolao Management Component', () => {
        let comp: BolaoComponent;
        let fixture: ComponentFixture<BolaoComponent>;
        let service: BolaoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [BolaoComponent],
                providers: [
                    BolaoService
                ]
            })
            .overrideTemplate(BolaoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BolaoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BolaoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Bolao(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bolaos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
