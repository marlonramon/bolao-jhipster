import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ApostaComponent } from './aposta.component';
export const apostaRoute: Routes = [
    {
        path: 'aposta',
        component: ApostaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apostas'
        },
        canActivate: [UserRouteAccessService]
    }, 
];

