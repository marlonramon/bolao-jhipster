/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BolaoTestModule } from '../../../test.module';
import { ClubeComponent } from '../../../../../../main/webapp/app/entities/clube/clube.component';
import { ClubeService } from '../../../../../../main/webapp/app/entities/clube/clube.service';
import { Clube } from '../../../../../../main/webapp/app/entities/clube/clube.model';

describe('Component Tests', () => {

    describe('Clube Management Component', () => {
        let comp: ClubeComponent;
        let fixture: ComponentFixture<ClubeComponent>;
        let service: ClubeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [ClubeComponent],
                providers: [
                    ClubeService
                ]
            })
            .overrideTemplate(ClubeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClubeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClubeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Clube(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.clubes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
