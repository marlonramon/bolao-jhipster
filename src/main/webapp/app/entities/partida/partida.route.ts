import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PartidaComponent } from './partida.component';
import { PartidaDetailComponent } from './partida-detail.component';
import { PartidaPopupComponent } from './partida-dialog.component';
import { PartidaDeletePopupComponent } from './partida-delete-dialog.component';

export const partidaRoute: Routes = [
    {
        path: 'partida',
        component: PartidaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Partidas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'partida/:id',
        component: PartidaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Partidas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const partidaPopupRoute: Routes = [
    {
        path: 'partida-new',
        component: PartidaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Partidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'partida/:id/edit',
        component: PartidaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Partidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'partida/:id/delete',
        component: PartidaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Partidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
