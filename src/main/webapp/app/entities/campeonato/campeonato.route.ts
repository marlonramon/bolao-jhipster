import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CampeonatoComponent } from './campeonato.component';
import { CampeonatoDetailComponent } from './campeonato-detail.component';
import { CampeonatoPopupComponent } from './campeonato-dialog.component';
import { CampeonatoDeletePopupComponent } from './campeonato-delete-dialog.component';

export const campeonatoRoute: Routes = [
    {
        path: 'campeonato',
        component: CampeonatoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campeonatoes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'campeonato/:id',
        component: CampeonatoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campeonatoes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const campeonatoPopupRoute: Routes = [
    {
        path: 'campeonato-new',
        component: CampeonatoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campeonatoes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'campeonato/:id/edit',
        component: CampeonatoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campeonatoes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'campeonato/:id/delete',
        component: CampeonatoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Campeonatoes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
