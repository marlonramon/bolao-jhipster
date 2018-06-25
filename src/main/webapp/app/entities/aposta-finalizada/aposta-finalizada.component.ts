import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import * as Chartist from 'chartist';


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
    selector: 'jhi-aposta-finalizada',
    templateUrl: './aposta-finalizada.component.html',
    styleUrls: ['aposta-finalizada.scss']
    
})
export class ApostaFinalizadaComponent implements OnInit, OnDestroy {

    rodadas: Rodada[];    
    @Input() apostas: Aposta[];    
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

    startAnimationForLineChart(chart){
        let seq: any, delays: any, durations: any;
        seq = 0;
        delays = 80;
        durations = 500;
  
        chart.on('draw', function(data) {
          if(data.type === 'line' || data.type === 'area') {
            data.element.animate({
              d: {
                begin: 600,
                dur: 700,
                from: data.path.clone().scale(1, 0).translate(0, data.chartRect.height()).stringify(),
                to: data.path.clone().stringify(),
                easing: Chartist.Svg.Easing.easeOutQuint
              }
            });
          } else if(data.type === 'point') {
                seq++;
                data.element.animate({
                  opacity: {
                    begin: seq * delays,
                    dur: durations,
                    from: 0,
                    to: 1,
                    easing: 'ease'
                  }
                });
            }
        });
  
        seq = 0;
    };

    loadApostas(rodada) {
        this.rodada_ativa = rodada;
        this.apostaService.queryByLoginAndRodada(this.login, rodada.id).subscribe(
            (res: HttpResponse<Aposta[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
 
    private onSuccess(data, headers) {
        this.apostas = [];
        const partidas = [];
        const pontuacao = [];
        for (let i = 0; i < data.length; i++) {
            let aposta = data[i]
            this.apostas.push(aposta);
            partidas.push(i+1);
            pontuacao.push(aposta.pontuacao);
        }
        
        const dataCompletedTasksChart: any = {
            labels: partidas,
            series: [
                pontuacao
            ]
        };
  
       const optionsCompletedTasksChart: any = {
            lineSmooth: Chartist.Interpolation.cardinal({
                tension: 0
            }),
            low: 0,
            high: 25, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
            chartPadding: { top: 0, right: 0, bottom: 0, left: 0}
        }
  
        var completedTasksChart = new Chartist.Line('#completedTasksChart', dataCompletedTasksChart, optionsCompletedTasksChart);
  
        // start animation for the Completed Tasks Chart - Line Chart
        this.startAnimationForLineChart(completedTasksChart);

    }

    montarMensagemResultado(aposta) {

        const mandante = aposta.partida.clubeMandante.nome;
        const visitante = aposta.partida.clubeVisitante.nome;

        const placarMandante = aposta.partida.placar ? aposta.partida.placar.placarMandante : undefined;
        const placarVisitante = aposta.partida.placar ? aposta.partida.placar.placarVisitante : undefined;

        let mensagemResultado = "Não definido";

        if (placarMandante != null && placarVisitante != null ) {
            mensagemResultado = mandante + "  " + placarMandante + " x " + placarVisitante + "  "  + visitante ;
        }

        return mensagemResultado;
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
