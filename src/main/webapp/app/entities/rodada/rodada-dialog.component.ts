import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rodada } from './rodada.model';
import { RodadaPopupService } from './rodada-popup.service';
import { RodadaService } from './rodada.service';
import { Campeonato, CampeonatoService } from '../campeonato';

@Component({
    selector: 'jhi-rodada-dialog',
    templateUrl: './rodada-dialog.component.html'
})
export class RodadaDialogComponent implements OnInit {

    rodada: Rodada;
    isSaving: boolean;

    campeonatoes: Campeonato[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rodadaService: RodadaService,
        private campeonatoService: CampeonatoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.campeonatoService.query()
            .subscribe((res: HttpResponse<Campeonato[]>) => { this.campeonatoes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rodada.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rodadaService.update(this.rodada));
        } else {
            this.subscribeToSaveResponse(
                this.rodadaService.create(this.rodada));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Rodada>>) {
        result.subscribe((res: HttpResponse<Rodada>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Rodada) {
        this.eventManager.broadcast({ name: 'rodadaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCampeonatoById(index: number, item: Campeonato) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rodada-popup',
    template: ''
})
export class RodadaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rodadaPopupService: RodadaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rodadaPopupService
                    .open(RodadaDialogComponent as Component, params['id']);
            } else {
                this.rodadaPopupService
                    .open(RodadaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
