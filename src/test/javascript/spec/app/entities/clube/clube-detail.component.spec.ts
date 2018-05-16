/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BolaoTestModule } from '../../../test.module';
import { ClubeDetailComponent } from '../../../../../../main/webapp/app/entities/clube/clube-detail.component';
import { ClubeService } from '../../../../../../main/webapp/app/entities/clube/clube.service';
import { Clube } from '../../../../../../main/webapp/app/entities/clube/clube.model';

describe('Component Tests', () => {

    describe('Clube Management Detail Component', () => {
        let comp: ClubeDetailComponent;
        let fixture: ComponentFixture<ClubeDetailComponent>;
        let service: ClubeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [ClubeDetailComponent],
                providers: [
                    ClubeService
                ]
            })
            .overrideTemplate(ClubeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClubeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClubeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Clube(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.clube).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
