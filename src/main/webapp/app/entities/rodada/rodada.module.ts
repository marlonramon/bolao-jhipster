import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import {
    RodadaService,
    RodadaPopupService,
    RodadaComponent,
    RodadaDetailComponent,
    RodadaDialogComponent,
    RodadaPopupComponent,
    RodadaDeletePopupComponent,
    RodadaDeleteDialogComponent,
    rodadaRoute,
    rodadaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rodadaRoute,
    ...rodadaPopupRoute,
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RodadaComponent,
        RodadaDetailComponent,
        RodadaDialogComponent,
        RodadaDeleteDialogComponent,
        RodadaPopupComponent,
        RodadaDeletePopupComponent,
    ],
    entryComponents: [
        RodadaComponent,
        RodadaDialogComponent,
        RodadaPopupComponent,
        RodadaDeleteDialogComponent,
        RodadaDeletePopupComponent,
    ],
    providers: [
        RodadaService,
        RodadaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoRodadaModule {}
