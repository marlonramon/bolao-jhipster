/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BolaoTestModule } from '../../../test.module';
import { ClubeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/clube/clube-delete-dialog.component';
import { ClubeService } from '../../../../../../main/webapp/app/entities/clube/clube.service';

describe('Component Tests', () => {

    describe('Clube Management Delete Component', () => {
        let comp: ClubeDeleteDialogComponent;
        let fixture: ComponentFixture<ClubeDeleteDialogComponent>;
        let service: ClubeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [ClubeDeleteDialogComponent],
                providers: [
                    ClubeService
                ]
            })
            .overrideTemplate(ClubeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClubeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClubeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
