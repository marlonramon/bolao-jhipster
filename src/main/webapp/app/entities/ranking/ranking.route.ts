import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RankingComponent } from './ranking.component';
export const rankingRoute: Routes = [
    {
        path: 'ranking',
        component: RankingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ranking'
        },
        canActivate: [UserRouteAccessService]
    }, 
];

