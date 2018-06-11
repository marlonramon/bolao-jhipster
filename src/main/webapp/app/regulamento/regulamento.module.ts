import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../shared';

import { HOME_ROUTE, RegulamentoComponent } from './';

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild([ HOME_ROUTE ])
    ],
    declarations: [
        RegulamentoComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoRegulamentoModule {}
