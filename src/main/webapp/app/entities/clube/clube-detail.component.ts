import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Clube } from './clube.model';
import { ClubeService } from './clube.service';

@Component({
    selector: 'jhi-clube-detail',
    templateUrl: './clube-detail.component.html'
})
export class ClubeDetailComponent implements OnInit, OnDestroy {

    clube: Clube;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private clubeService: ClubeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClubes();
    }

    load(id) {
        this.clubeService.find(id)
            .subscribe((clubeResponse: HttpResponse<Clube>) => {
                this.clube = clubeResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClubes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clubeListModification',
            (response) => this.load(this.clube.id)
        );
    }
}
