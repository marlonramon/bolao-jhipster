import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Campeonato } from './campeonato.model';
import { CampeonatoService } from './campeonato.service';

@Component({
    selector: 'jhi-campeonato-detail',
    templateUrl: './campeonato-detail.component.html'
})
export class CampeonatoDetailComponent implements OnInit, OnDestroy {

    campeonato: Campeonato;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private campeonatoService: CampeonatoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCampeonatoes();
    }

    load(id) {
        this.campeonatoService.find(id)
            .subscribe((campeonatoResponse: HttpResponse<Campeonato>) => {
                this.campeonato = campeonatoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCampeonatoes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'campeonatoListModification',
            (response) => this.load(this.campeonato.id)
        );
    }
}
