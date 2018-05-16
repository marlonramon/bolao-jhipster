import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BolaoComponent } from './bolao.component';
import { BolaoDetailComponent } from './bolao-detail.component';
import { BolaoPopupComponent } from './bolao-dialog.component';
import { BolaoDeletePopupComponent } from './bolao-delete-dialog.component';

export const bolaoRoute: Routes = [
    {
        path: 'bolao',
        component: BolaoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bolaos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bolao/:id',
        component: BolaoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bolaos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bolaoPopupRoute: Routes = [
    {
        path: 'bolao-new',
        component: BolaoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bolaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bolao/:id/edit',
        component: BolaoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bolaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bolao/:id/delete',
        component: BolaoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bolaos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
