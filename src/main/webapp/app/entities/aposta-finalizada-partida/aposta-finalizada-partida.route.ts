import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ApostaFinalizadaPartidaComponent } from './aposta-finalizada-partida.component';
export const apostaFinalizadaPartidaRoute: Routes = [
    {
        path: 'apostas-finalizadas-partida/:idPartida',
        component: ApostaFinalizadaPartidaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Partida - Apostas Finalizadas '
        },
        canActivate: [UserRouteAccessService]
    }, 
];

