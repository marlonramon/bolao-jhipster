import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import {
    ApostaService,
    ApostaComponent,
    apostaRoute,
} from './';

const ENTITY_STATES = [
    ...apostaRoute,
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ApostaComponent,        
    ],
    entryComponents: [
        ApostaComponent,
        
    ],
    providers: [
        ApostaService,        
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoApostaModule {}
