import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ApostaFinalizadaComponent } from './aposta-finalizada.component';
export const apostaFinalizadaRoute: Routes = [
    {
        path: 'apostas-finalizadas/:login',
        component: ApostaFinalizadaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Apostas Finalizadas'
        },
        canActivate: [UserRouteAccessService]
    }, 
];

