import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDateUtils } from 'ng-jhipster';


import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/forkJoin';
import { Aposta } from '../aposta/aposta.model';
import { Rodada } from '../rodada/rodada.model';
import { Bolao } from '../bolao/bolao.model';
import { User } from '../../shared';
import { ApostaService } from '../aposta/aposta.service';
import { RodadaService } from '../rodada/rodada.service';
import { UserService } from '../../shared';
import { BolaoService } from '../bolao/bolao.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';

@Component({
    selector: 'jhi-aposta',
    templateUrl: './aposta-finalizada.component.html'
    
})
export class ApostaFinalizadaComponent implements OnInit, OnDestroy {

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
    routeSub: any;
    user: User;
    login : string;
  

    constructor(
        private apostaService: ApostaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private bolaoService: BolaoService,
        private rodadaService: RodadaService,
        private userService: UserService,
        private route: ActivatedRoute,
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
        this.user = null;
        this.login = '';
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['login'] ) {
                this.login = params['login'];
                this.loadAll();
            } 
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

    loadAll() {
        this.userService.find(this.login).subscribe(
            (res: HttpResponse<User>) => {this.onSuccessUser(res.body, res.headers)},
            (res: HttpErrorResponse) => this.onError(res.message) );
        
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


    trackId(index: number, item: Aposta) {
        return item.id;
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

    loadApostas(rodada) {
        this.rodada_ativa = rodada;
        this.apostaService.queryByLoginAndRodada(this.login, rodada.id).subscribe(
            (res: HttpResponse<Aposta[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
 
    private onSuccess(data, headers) {
        this.apostas = [];
        for (let i = 0; i < data.length; i++) {
            this.apostas.push(data[i]);
        }
        
    }

    private onSuccessUser(data, headers) {
        this.user = data;
        this.bolaoService.queryByLoggedUser().subscribe(
            (res: HttpResponse<Aposta[]>) => {this.onSuccessBolao(res.body, res.headers)},
            (res: HttpErrorResponse) => this.onError(res.message),

        );    
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
