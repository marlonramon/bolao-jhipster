import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import {
    PartidaService,
    PartidaPopupService,
    PartidaComponent,
    PartidaDetailComponent,
    PartidaDialogComponent,
    PartidaPopupComponent,
    PartidaDeletePopupComponent,
    PartidaDeleteDialogComponent,
    partidaRoute,
    partidaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...partidaRoute,
    ...partidaPopupRoute,
];

@NgModule({
    imports: [
        BolaoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PartidaComponent,
        PartidaDetailComponent,
        PartidaDialogComponent,
        PartidaDeleteDialogComponent,
        PartidaPopupComponent,
        PartidaDeletePopupComponent,
    ],
    entryComponents: [
        PartidaComponent,
        PartidaDialogComponent,
        PartidaPopupComponent,
        PartidaDeleteDialogComponent,
        PartidaDeletePopupComponent,
    ],
    providers: [
        PartidaService,
        PartidaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoPartidaModule {}
