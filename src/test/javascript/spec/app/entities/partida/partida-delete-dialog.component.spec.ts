/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BolaoTestModule } from '../../../test.module';
import { PartidaDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/partida/partida-delete-dialog.component';
import { PartidaService } from '../../../../../../main/webapp/app/entities/partida/partida.service';

describe('Component Tests', () => {

    describe('Partida Management Delete Component', () => {
        let comp: PartidaDeleteDialogComponent;
        let fixture: ComponentFixture<PartidaDeleteDialogComponent>;
        let service: PartidaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BolaoTestModule],
                declarations: [PartidaDeleteDialogComponent],
                providers: [
                    PartidaService
                ]
            })
            .overrideTemplate(PartidaDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PartidaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartidaService);
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
