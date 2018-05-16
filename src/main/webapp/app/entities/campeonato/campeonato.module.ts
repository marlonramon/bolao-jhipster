import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import {
    CampeonatoService,
    CampeonatoPopupService,
    CampeonatoComponent,
    CampeonatoDetailComponent,
    CampeonatoDialogComponent,
    CampeonatoPopupComponent,
    CampeonatoDeletePopupComponent,
    CampeonatoDeleteDialogComponent,
    campeonatoRoute,
    campeonatoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...campeonatoRoute,
    ...campeonatoPopupRoute,
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CampeonatoComponent,
        CampeonatoDetailComponent,
        CampeonatoDialogComponent,
        CampeonatoDeleteDialogComponent,
        CampeonatoPopupComponent,
        CampeonatoDeletePopupComponent,
    ],
    entryComponents: [
        CampeonatoComponent,
        CampeonatoDialogComponent,
        CampeonatoPopupComponent,
        CampeonatoDeleteDialogComponent,
        CampeonatoDeletePopupComponent,
    ],
    providers: [
        CampeonatoService,
        CampeonatoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoCampeonatoModule {}
