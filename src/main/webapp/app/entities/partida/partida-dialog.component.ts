import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Partida } from './partida.model';
import { PartidaPopupService } from './partida-popup.service';
import { PartidaService } from './partida.service';
import { Clube, ClubeService } from '../clube';
import { Rodada, RodadaService } from '../rodada';

@Component({
    selector: 'jhi-partida-dialog',
    templateUrl: './partida-dialog.component.html'
})
export class PartidaDialogComponent implements OnInit {

    partida: Partida;
    isSaving: boolean;

    clubes: Clube[];

    rodadas: Rodada[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private partidaService: PartidaService,
        private clubeService: ClubeService,
        private rodadaService: RodadaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.clubeService.query()
            .subscribe((res: HttpResponse<Clube[]>) => { this.clubes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.rodadaService.query()
            .subscribe((res: HttpResponse<Rodada[]>) => { this.rodadas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.partida.id !== undefined) {
            this.subscribeToSaveResponse(
                this.partidaService.update(this.partida));
        } else {
            this.subscribeToSaveResponse(
                this.partidaService.create(this.partida));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Partida>>) {
        result.subscribe((res: HttpResponse<Partida>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Partida) {
        this.eventManager.broadcast({ name: 'partidaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackClubeById(index: number, item: Clube) {
        return item.id;
    }

    trackRodadaById(index: number, item: Rodada) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-partida-popup',
    template: ''
})
export class PartidaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private partidaPopupService: PartidaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.partidaPopupService
                    .open(PartidaDialogComponent as Component, params['id']);
            } else {
                this.partidaPopupService
                    .open(PartidaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
