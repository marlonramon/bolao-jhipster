import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bolao } from './bolao.model';
import { BolaoService } from './bolao.service';

@Component({
    selector: 'jhi-bolao-detail',
    templateUrl: './bolao-detail.component.html'
})
export class BolaoDetailComponent implements OnInit, OnDestroy {

    bolao: Bolao;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bolaoService: BolaoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBolaos();
    }

    load(id) {
        this.bolaoService.find(id)
            .subscribe((bolaoResponse: HttpResponse<Bolao>) => {
                this.bolao = bolaoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBolaos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bolaoListModification',
            (response) => this.load(this.bolao.id)
        );
    }
}
