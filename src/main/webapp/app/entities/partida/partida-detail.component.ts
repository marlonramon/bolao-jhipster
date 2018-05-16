import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Partida } from './partida.model';
import { PartidaService } from './partida.service';

@Component({
    selector: 'jhi-partida-detail',
    templateUrl: './partida-detail.component.html'
})
export class PartidaDetailComponent implements OnInit, OnDestroy {

    partida: Partida;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private partidaService: PartidaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPartidas();
    }

    load(id) {
        this.partidaService.find(id)
            .subscribe((partidaResponse: HttpResponse<Partida>) => {
                this.partida = partidaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPartidas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'partidaListModification',
            (response) => this.load(this.partida.id)
        );
    }
}
