import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDateUtils } from 'ng-jhipster';
import * as Chartist from 'chartist';


import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/forkJoin';
import { Aposta } from '../aposta/aposta.model';
import { Partida } from '../partida/partida.model';
import { Bolao } from '../bolao/bolao.model';
import { User } from '../../shared';
import { ApostaService } from '../aposta/aposta.service';
import { PartidaService } from '../partida/partida.service';
import { UserService } from '../../shared';
import { BolaoService } from '../bolao/bolao.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';

@Component({
    selector: 'jhi-aposta-finalizada-partida',
    templateUrl: './aposta-finalizada-partida.component.html',
    styleUrls: ['aposta-finalizada.scss']

})
export class ApostaFinalizadaPartidaComponent implements OnInit, OnDestroy {

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
    partida: Partida;
    routeSub: any;
    user: User;
    idPartida: number;



    constructor(
        private apostaService: ApostaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private bolaoService: BolaoService,
        private partidaService: PartidaService,
        private userService: UserService,
        private route: ActivatedRoute,
        private dateUtils: JhiDateUtils

    ) {
        this.apostas = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.user = null;
        this.idPartida = 0;
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['idPartida']) {
                this.idPartida = params['idPartida'];
                this.partidaService.find(this.idPartida)
                    .subscribe((partidaResponse: HttpResponse<Partida>) => {
                        this.partida = partidaResponse.body;
                        this.loadAll();
                    });
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }

    loadAll() {

        this.apostaService.queryByPartidaFinalizada(this.idPartida).subscribe(
            (res: HttpResponse<Aposta[]>) => { this.onSuccessApostas(res.body) },
            (res: HttpErrorResponse) => this.onError(res.error));

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


    loadBandeira(bandeira) {
        return require('../../../content/images/bandeiras/' + bandeira);
    }

    private onSuccessApostas(data) {
        this.apostas = [];
        for (let i = 0; i < data.length; i++) {
            this.apostas.push(data[i]);
        }

    }


    montarMensagemResultado(partida) {

        const mandante = partida.clubeMandante.nome;
        const visitante = partida.clubeVisitante.nome;

        const placarMandante = partida.placar ? partida.placar.placarMandante : undefined;
        const placarVisitante = partida.placar ? partida.placar.placarVisitante : undefined;

        let mensagemResultado = mandante + " x " + visitante ;

        if (placarMandante != null && placarVisitante != null ) {
            mensagemResultado = mandante + "  " + placarMandante + " x " + placarVisitante + "  "  + visitante ;
        }

        return mensagemResultado;
    }

    private onError(error) {
        console.log('erro ' + error);
        this.jhiAlertService.error(error.message, null, null);

    }
}
