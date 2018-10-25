import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import {
    ApostaService,
    ApostaFinalizadaPartidaComponent,
    apostaFinalizadaPartidaRoute,
} from './';

const ENTITY_STATES = [
    ...apostaFinalizadaPartidaRoute,
    
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ApostaFinalizadaPartidaComponent,        
    ],
    entryComponents: [
        ApostaFinalizadaPartidaComponent,
        
    ],
    providers: [
        ApostaService,        
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoApostaFinalizadaPartidaModule {}
