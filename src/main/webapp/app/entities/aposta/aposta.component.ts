import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDateUtils } from 'ng-jhipster';


import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/forkJoin';
import { Aposta } from './aposta.model';
import { Rodada } from '../rodada/rodada.model';
import { Bolao } from '../bolao/bolao.model';
import { ApostaService } from './aposta.service';
import { RodadaService } from '../rodada/rodada.service';
import { BolaoService } from '../bolao/bolao.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';

@Component({
    selector: 'jhi-aposta',
    templateUrl: './aposta.component.html'
    
})
export class ApostaComponent implements OnInit, OnDestroy {

    

    rodadas: Rodada[];
    apostas: Aposta[];
    isSaving: boolean;
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
    rodada_ativa: Rodada;
  

    constructor(
        private apostaService: ApostaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private bolaoService: BolaoService,
        private rodadaService: RodadaService,
        private dateUtils: JhiDateUtils
        
    ) {
        this.apostas = [];
        this.boloes = [];
        this.rodadas = [];
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
            (res: HttpResponse<Aposta[]>) => {this.onSuccessBolao(res.body, res.headers)},
            (res: HttpErrorResponse) => this.onError(res.message),

        );
        
    }

    reset() {
        this.page = 0;
        this.apostas = [];
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

    trackId(index: number, item: Aposta) {
        return item.id;
    }
    registerChangeInApostas() {
        this.eventSubscriber = this.eventManager.subscribe('apostaListModification', (response) => this.reset());
    }

    isPartidaIniciada(partida) {
        let dataPartida = this.dateUtils.toDate(partida.dataPartida);
        let currentDate = new Date();
        return dataPartida.getTime() < currentDate.getTime();
    }

    loadRodadas(idCampeonato) {
        this.rodadaService.queryByCampeonato(idCampeonato).subscribe(
            (res: HttpResponse<Rodada[]>) => this.onSuccessRodada(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    loadBandeira(bandeira) {
        return require('../../../content/images/bandeiras/' + bandeira);
    }

    salvarApostas() {
        
        const calls = []
        this.isSaving = true;
        this.apostas.forEach(aposta => {
            let isPartidaIniciada = this.isPartidaIniciada(aposta.partida);
            if (!isPartidaIniciada) {                
                calls.push(this.save(aposta))
            }    
        });

        Observable.forkJoin(calls).subscribe( 
            data => {
                this.jhiAlertService.success("Aposta salvas com sucesso.");              

            },
            (res: HttpErrorResponse) => this.onError(res.error)
        );



        this.isSaving = false;        

        this.loadApostas(this.rodadas[0]);
        
    }

    save(aposta): Observable<HttpResponse<Aposta>> {
        if (aposta.id !== undefined) {
            return this.apostaService.update(aposta);
        } else {
            return  this.apostaService.create(aposta);
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Aposta>>) {
        result.subscribe((res: HttpResponse<Aposta>) =>(res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Aposta) {
        this.eventManager.broadcast({ name: 'apostaListModification', content: 'OK'});
        
    }

    loadApostas(rodada) {
        this.rodada_ativa = rodada;
        this.apostaService.queryByLoggedUserAndRodada(rodada.id).subscribe(
            (res: HttpResponse<Aposta[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    
    private onSaveError() {
        this.isSaving = false;
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.apostas = [];
        for (let i = 0; i < data.length; i++) {
            this.apostas.push(data[i]);
        }
        
    }

    private onSuccessBolao(data, headers) {
        for (let i = 0; i < data.length; i++) {
            this.bolao = data[i];            
            this.loadRodadas(this.bolao.campeonatoDTO.id);
        }
    }

    private definirRodadaAtual() : Rodada {
        let rodadarodada

        for (let index = 0; index <  this.rodadas.length; index++) {
            let rodada = this.rodadas[index];
            
            let dataRodada = this.dateUtils.toDate(rodada.fimRodada)
            let dataAtual = new Date();
            
            if (dataAtual.getTime() < dataRodada.getTime()) {
                return rodada;                
            }            
        }        

    }

    isRodadaAtiva(rodada) {
        return rodada === this.rodada_ativa;
    }

    private onSuccessRodada(data, headers) {
        for (let i = 0; i < data.length; i++) {
            let rodada = data[i];
            this.rodadas.push(rodada);            
        }

        this.rodada_ativa =  this.definirRodadaAtual();
        
        this.loadApostas(this.rodada_ativa);
        

    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
        
    }
}
