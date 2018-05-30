import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/forkJoin';
import { Ranking } from './ranking.model';
import { Rodada } from '../rodada/rodada.model';
import { Bolao } from '../bolao/bolao.model';
import { RankingService } from './ranking.service';
import { RodadaService } from '../rodada/rodada.service';
import { BolaoService } from '../bolao/bolao.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';

@Component({
    selector: 'jhi-ranking',
    templateUrl: './ranking.component.html'
    
})
export class RankingComponent implements OnInit, OnDestroy {

    ranking: Ranking[];
    
    boloes: Bolao[];
    bolao: Bolao;
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;

    constructor(
        private rankingService: RankingService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private bolaoService: BolaoService,
        private rodadaService: RodadaService
    ) {
        this.boloes = [];
        this.ranking = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        
        this.bolaoService.queryByLoggedUser().subscribe(
            (res: HttpResponse<Bolao[]>) => {this.onSuccessBolao(res.body, res.headers)},
            (res: HttpErrorResponse) => this.onError(res.message),

        );
        
    }

    reset() {
        this.page = 0;
        this.ranking = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInApostas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    
    registerChangeInApostas() {
        this.eventSubscriber = this.eventManager.subscribe('apostaListModification', (response) => this.reset());
    }
   

    private onSuccessBolao(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.bolao = data[i];                        
            this.loadRanking(this.bolao);
        }
    }

    private loadRanking(bolao) {
        this.rankingService.queryByBolao(bolao.id).subscribe(
            (res: HttpResponse<Ranking[]>) => this.onSuccessRanking(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onSuccessRanking(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.ranking.push(data[i]);                        
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
