import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ApostaComponent } from './aposta.component';
import { ApostaDetailComponent } from './aposta-detail.component';
import { ApostaPopupComponent } from './aposta-dialog.component';
import { ApostaDeletePopupComponent } from './aposta-delete-dialog.component';

export const apostaRoute: Routes = [
    {
        path: 'aposta',
        component: ApostaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apostas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'aposta/:id',
        component: ApostaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apostas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const apostaPopupRoute: Routes = [
    {
        path: 'aposta-new',
        component: ApostaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apostas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aposta/:id/edit',
        component: ApostaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apostas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aposta/:id/delete',
        component: ApostaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apostas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
