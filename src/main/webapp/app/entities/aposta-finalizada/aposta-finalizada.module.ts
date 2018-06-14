import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import {
    ApostaService,
    ApostaFinalizadaComponent,
    apostaFinalizadaRoute,
    
} from './';

const ENTITY_STATES = [
    ...apostaFinalizadaRoute,
    
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ApostaFinalizadaComponent,        
    ],
    entryComponents: [
        ApostaFinalizadaComponent,
        
    ],
    providers: [
        ApostaService,        
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoApostaFinalizadaModule {}
