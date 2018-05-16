import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Rodada } from './rodada.model';
import { RodadaService } from './rodada.service';

@Component({
    selector: 'jhi-rodada-detail',
    templateUrl: './rodada-detail.component.html'
})
export class RodadaDetailComponent implements OnInit, OnDestroy {

    rodada: Rodada;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private rodadaService: RodadaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRodadas();
    }

    load(id) {
        this.rodadaService.find(id)
            .subscribe((rodadaResponse: HttpResponse<Rodada>) => {
                this.rodada = rodadaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRodadas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'rodadaListModification',
            (response) => this.load(this.rodada.id)
        );
    }
}
