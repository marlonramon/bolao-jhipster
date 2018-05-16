import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BolaoSharedModule } from '../../shared';
import { BolaoAdminModule } from '../../admin/admin.module';
import {
    BolaoService,
    BolaoPopupService,
    BolaoComponent,
    BolaoDetailComponent,
    BolaoDialogComponent,
    BolaoPopupComponent,
    BolaoDeletePopupComponent,
    BolaoDeleteDialogComponent,
    bolaoRoute,
    bolaoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bolaoRoute,
    ...bolaoPopupRoute,
];

@NgModule({
    imports: [
        BolaoSharedModule,
        BolaoAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BolaoComponent,
        BolaoDetailComponent,
        BolaoDialogComponent,
        BolaoDeleteDialogComponent,
        BolaoPopupComponent,
        BolaoDeletePopupComponent,
    ],
    entryComponents: [
        BolaoComponent,
        BolaoDialogComponent,
        BolaoPopupComponent,
        BolaoDeleteDialogComponent,
        BolaoDeletePopupComponent,
    ],
    providers: [
        BolaoService,
        BolaoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BolaoBolaoModule {}
