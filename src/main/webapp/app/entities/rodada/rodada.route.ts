import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RodadaComponent } from './rodada.component';
import { RodadaDetailComponent } from './rodada-detail.component';
import { RodadaPopupComponent } from './rodada-dialog.component';
import { RodadaDeletePopupComponent } from './rodada-delete-dialog.component';

export const rodadaRoute: Routes = [
    {
        path: 'rodada',
        component: RodadaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rodadas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'rodada/:id',
        component: RodadaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rodadas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rodadaPopupRoute: Routes = [
    {
        path: 'rodada-new',
        component: RodadaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rodadas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rodada/:id/edit',
        component: RodadaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rodadas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'rodada/:id/delete',
        component: RodadaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rodadas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
