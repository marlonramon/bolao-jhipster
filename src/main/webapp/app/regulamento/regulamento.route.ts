import { Route } from '@angular/router';

import { RegulamentoComponent } from './';

export const HOME_ROUTE: Route = {
    path: 'regulamento',
    component: RegulamentoComponent,
    data: {
        authorities: [],
        pageTitle: 'Bolão - Regulamento'
    }
};
