import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';

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

import { OWL_DATE_TIME_LOCALE } from 'ng-pick-datetime';

const ENTITY_STATES = [
    ...rodadaRoute,
    ...rodadaPopupRoute,
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        OwlDateTimeModule, 
        OwlNativeDateTimeModule,
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
        {provide: OWL_DATE_TIME_LOCALE, useValue: 'pt-br'},
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoRodadaModule {}
