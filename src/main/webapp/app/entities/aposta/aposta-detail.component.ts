import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Aposta } from './aposta.model';
import { ApostaService } from './aposta.service';

@Component({
    selector: 'jhi-aposta-detail',
    templateUrl: './aposta-detail.component.html'
})
export class ApostaDetailComponent implements OnInit, OnDestroy {

    aposta: Aposta;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private apostaService: ApostaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInApostas();
    }

    load(id) {
        this.apostaService.find(id)
            .subscribe((apostaResponse: HttpResponse<Aposta>) => {
                this.aposta = apostaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInApostas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'apostaListModification',
            (response) => this.load(this.aposta.id)
        );
    }
}
