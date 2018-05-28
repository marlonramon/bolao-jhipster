import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Partida } from './partida.model';
import { Placar } from '../../shared';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Partida>;

@Injectable()
export class PartidaService {

    private resourceUrl =  SERVER_API_URL + 'api/partidas';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(partida: Partida): Observable<EntityResponseType> {
        const copy = this.convert(partida);
        return this.http.post<Partida>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(partida: Partida): Observable<EntityResponseType> {
        const copy = this.convert(partida);
        return this.http.put<Partida>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Partida>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Partida[]>> {
        const options = createRequestOption(req);
        return this.http.get<Partida[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Partida[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Partida = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Partida[]>): HttpResponse<Partida[]> {
        const jsonResponse: Partida[] = res.body;
        const body: Partida[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Partida.
     */
    private convertItemFromServer(partida: Partida): Partida {
        const copy: Partida = Object.assign({}, partida);
        copy.dataPartida = this.dateUtils
            .convertDateTimeFromServer(partida.dataPartida);

        let placar : Placar = {

        }
        if(!copy.placar) {
            copy.placar = placar;
        }    


        return copy;
    }

    /**
     * Convert a Partida to a JSON which can be sent to the server.
     */
    private convert(partida: Partida): Partida {
        const copy: Partida = Object.assign({}, partida);

        copy.dataPartida = this.dateUtils.toDate(partida.dataPartida);

        

        return copy;
    }
}
