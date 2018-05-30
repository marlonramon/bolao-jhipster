import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Ranking } from './ranking.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Ranking>;

@Injectable()
export class RankingService {

    private resourceUrl =  SERVER_API_URL + 'api/apostas';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

  
    queryByBolao(idBolao: number): Observable<HttpResponse<Ranking[]>> {
        
        return this.http.get<Ranking[]>(`api/bolao/${idBolao}/ranking`, {observe: 'response' })
            .map((res: HttpResponse<Ranking[]>) => this.convertArrayResponse(res));
    }


    
    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Ranking = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Ranking[]>): HttpResponse<Ranking[]> {
        const jsonResponse: Ranking[] = res.body;
        const body: Ranking[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Aposta.
     */
    private convertItemFromServer(ranking: Ranking): Ranking {
        const copy: Ranking = Object.assign({}, ranking);        
        return copy;
    }

    /**
     * Convert a Aposta to a JSON which can be sent to the server.
     */
    private convert(aposta: Ranking): Ranking {
        const copy: Ranking = Object.assign({}, aposta);

        return copy;
    }
}
