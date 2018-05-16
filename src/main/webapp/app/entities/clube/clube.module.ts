import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import {
    ClubeService,
    ClubePopupService,
    ClubeComponent,
    ClubeDetailComponent,
    ClubeDialogComponent,
    ClubePopupComponent,
    ClubeDeletePopupComponent,
    ClubeDeleteDialogComponent,
    clubeRoute,
    clubePopupRoute,
} from './';

const ENTITY_STATES = [
    ...clubeRoute,
    ...clubePopupRoute,
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClubeComponent,
        ClubeDetailComponent,
        ClubeDialogComponent,
        ClubeDeleteDialogComponent,
        ClubePopupComponent,
        ClubeDeletePopupComponent,
    ],
    entryComponents: [
        ClubeComponent,
        ClubeDialogComponent,
        ClubePopupComponent,
        ClubeDeleteDialogComponent,
        ClubeDeletePopupComponent,
    ],
    providers: [
        ClubeService,
        ClubePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoClubeModule {}
