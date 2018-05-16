import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Clube } from './clube.model';
import { ClubePopupService } from './clube-popup.service';
import { ClubeService } from './clube.service';

@Component({
    selector: 'jhi-clube-dialog',
    templateUrl: './clube-dialog.component.html'
})
export class ClubeDialogComponent implements OnInit {

    clube: Clube;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private clubeService: ClubeService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.clube, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clube.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clubeService.update(this.clube));
        } else {
            this.subscribeToSaveResponse(
                this.clubeService.create(this.clube));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Clube>>) {
        result.subscribe((res: HttpResponse<Clube>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Clube) {
        this.eventManager.broadcast({ name: 'clubeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-clube-popup',
    template: ''
})
export class ClubePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clubePopupService: ClubePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.clubePopupService
                    .open(ClubeDialogComponent as Component, params['id']);
            } else {
                this.clubePopupService
                    .open(ClubeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
