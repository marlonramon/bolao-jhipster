import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BolaoBolaoModule } from './bolao/bolao.module';
import { BolaoCampeonatoModule } from './campeonato/campeonato.module';
import { BolaoRodadaModule } from './rodada/rodada.module';
import { BolaoPartidaModule } from './partida/partida.module';
import { BolaoClubeModule } from './clube/clube.module';
import { BolaoApostaModule } from './aposta/aposta.module';
import { BolaoRankingModule } from './ranking/ranking.module';
import { BolaoApostaFinalizadaModule } from './aposta-finalizada/aposta-finalizada.module';
import { BolaoApostaFinalizadaPartidaModule } from './aposta-finalizada-partida/aposta-finalizada-partida.module';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */



@NgModule({
    imports: [
        BolaoBolaoModule,
        BolaoCampeonatoModule,
        BolaoRodadaModule,
        BolaoPartidaModule,
        BolaoClubeModule,
        BolaoApostaModule, 
        BolaoRankingModule,
        BolaoApostaFinalizadaModule,
        BolaoApostaFinalizadaPartidaModule      
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoEntityModule {}
