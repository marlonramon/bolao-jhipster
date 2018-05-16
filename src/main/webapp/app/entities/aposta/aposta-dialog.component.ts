import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Aposta } from './aposta.model';
import { ApostaPopupService } from './aposta-popup.service';
import { ApostaService } from './aposta.service';
import { Partida, PartidaService } from '../partida';

@Component({
    selector: 'jhi-aposta-dialog',
    templateUrl: './aposta-dialog.component.html'
})
export class ApostaDialogComponent implements OnInit {

    aposta: Aposta;
    isSaving: boolean;

    partidas: Partida[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private apostaService: ApostaService,
        private partidaService: PartidaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.partidaService.query()
            .subscribe((res: HttpResponse<Partida[]>) => { this.partidas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.aposta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.apostaService.update(this.aposta));
        } else {
            this.subscribeToSaveResponse(
                this.apostaService.create(this.aposta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Aposta>>) {
        result.subscribe((res: HttpResponse<Aposta>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Aposta) {
        this.eventManager.broadcast({ name: 'apostaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPartidaById(index: number, item: Partida) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-aposta-popup',
    template: ''
})
export class ApostaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private apostaPopupService: ApostaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.apostaPopupService
                    .open(ApostaDialogComponent as Component, params['id']);
            } else {
                this.apostaPopupService
                    .open(ApostaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
