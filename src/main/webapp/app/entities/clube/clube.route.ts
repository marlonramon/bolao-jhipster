import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ClubeComponent } from './clube.component';
import { ClubeDetailComponent } from './clube-detail.component';
import { ClubePopupComponent } from './clube-dialog.component';
import { ClubeDeletePopupComponent } from './clube-delete-dialog.component';

export const clubeRoute: Routes = [
    {
        path: 'clube',
        component: ClubeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clubes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clube/:id',
        component: ClubeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clubes'
        },
        canActivate: [UserRouteAccessService]
    },   
    {
        path: 'clube-new',
        component: ClubePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clubes'
        },
        canActivate: [UserRouteAccessService],
        
    },
    {
        path: 'clube/:id/edit',
        component: ClubePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clubes'
        },
        canActivate: [UserRouteAccessService],
        
    },
    {
        path: 'clube/:id/delete',
        component: ClubeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clubes'
        },
        canActivate: [UserRouteAccessService],
        
    }
];

export const clubePopupRoute: Routes = [
    {
        path: 'clube-new',
        component: ClubePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clubes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clube/:id/edit',
        component: ClubePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clubes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clube/:id/delete',
        component: ClubeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Clubes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
