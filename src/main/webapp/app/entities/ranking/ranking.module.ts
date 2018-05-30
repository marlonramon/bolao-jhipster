import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import {
    RankingService,
    RankingComponent,
    rankingRoute,
    
} from './';

const ENTITY_STATES = [
    ...rankingRoute,
    
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RankingComponent,        
    ],
    entryComponents: [
        RankingComponent,
        
    ],
    providers: [
        RankingService,        
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoRankingModule {}
