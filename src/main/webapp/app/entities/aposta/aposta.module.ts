import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import {
    ApostaService,
    ApostaPopupService,
    ApostaComponent,
    ApostaDetailComponent,
    ApostaDialogComponent,
    ApostaPopupComponent,
    ApostaDeletePopupComponent,
    ApostaDeleteDialogComponent,
    apostaRoute,
    apostaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...apostaRoute,
    ...apostaPopupRoute,
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ApostaComponent,
        ApostaDetailComponent,
        ApostaDialogComponent,
        ApostaDeleteDialogComponent,
        ApostaPopupComponent,
        ApostaDeletePopupComponent,
    ],
    entryComponents: [
        ApostaComponent,
        ApostaDialogComponent,
        ApostaPopupComponent,
        ApostaDeleteDialogComponent,
        ApostaDeletePopupComponent,
    ],
    providers: [
        ApostaService,
        ApostaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoApostaModule {}
