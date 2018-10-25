import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Ranking } from './ranking.model';

export type EntityResponseType = HttpResponse<Ranking>;

@Injectable()
export class RankingService {

    constructor(private http: HttpClient) { }
  
    queryByBolao(idBolao: number): Observable<HttpResponse<Ranking[]>> {
        return this.http.get<Ranking[]>(`api/bolao/${idBolao}/ranking`, {observe: 'response' })
            .map((res: HttpResponse<Ranking[]>) => this.convertArrayResponse(res));
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
}
