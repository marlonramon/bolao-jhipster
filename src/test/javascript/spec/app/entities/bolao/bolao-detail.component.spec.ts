/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BolaoTestModule } from '../../../test.module';
import { BolaoDetailComponent } from '../../../../../../main/webapp/app/entities/bolao/bolao-detail.component';
import { BolaoService } from '../../../../../../main/webapp/app/entities/bolao/bolao.service';
import { Bolao } from '../../../../../../main/webapp/app/entities/bolao/bolao.model';

describe('Component Tests', () => {

    describe('Bolao Management Detail Component', () => {
        let comp: BolaoDetailComponent;
        let fixture: ComponentFixture<BolaoDetailComponent>;
        let service: BolaoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [BolaoDetailComponent],
                providers: [
                    BolaoService
                ]
            })
            .overrideTemplate(BolaoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BolaoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BolaoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Bolao(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bolao).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
