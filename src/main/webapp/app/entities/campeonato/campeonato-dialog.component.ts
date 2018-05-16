import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Campeonato } from './campeonato.model';
import { CampeonatoPopupService } from './campeonato-popup.service';
import { CampeonatoService } from './campeonato.service';
import { Bolao, BolaoService } from '../bolao';

@Component({
    selector: 'jhi-campeonato-dialog',
    templateUrl: './campeonato-dialog.component.html'
})
export class CampeonatoDialogComponent implements OnInit {

    campeonato: Campeonato;
    isSaving: boolean;

    bolaos: Bolao[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private campeonatoService: CampeonatoService,
        private bolaoService: BolaoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.bolaoService.query()
            .subscribe((res: HttpResponse<Bolao[]>) => { this.bolaos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.campeonato.id !== undefined) {
            this.subscribeToSaveResponse(
                this.campeonatoService.update(this.campeonato));
        } else {
            this.subscribeToSaveResponse(
                this.campeonatoService.create(this.campeonato));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Campeonato>>) {
        result.subscribe((res: HttpResponse<Campeonato>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Campeonato) {
        this.eventManager.broadcast({ name: 'campeonatoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBolaoById(index: number, item: Bolao) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-campeonato-popup',
    template: ''
})
export class CampeonatoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private campeonatoPopupService: CampeonatoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.campeonatoPopupService
                    .open(CampeonatoDialogComponent as Component, params['id']);
            } else {
                this.campeonatoPopupService
                    .open(CampeonatoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
