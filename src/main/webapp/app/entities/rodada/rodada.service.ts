import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Rodada } from './rodada.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Rodada>;

@Injectable()
export class RodadaService {

    private resourceUrl =  SERVER_API_URL + 'api/rodadas';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(rodada: Rodada): Observable<EntityResponseType> {
        const copy = this.convert(rodada);
        return this.http.post<Rodada>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(rodada: Rodada): Observable<EntityResponseType> {
        const copy = this.convert(rodada);
        return this.http.put<Rodada>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Rodada>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Rodada[]>> {
        const options = createRequestOption(req);
        return this.http.get<Rodada[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Rodada[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Rodada = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Rodada[]>): HttpResponse<Rodada[]> {
        const jsonResponse: Rodada[] = res.body;
        const body: Rodada[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Rodada.
     */
    private convertItemFromServer(rodada: Rodada): Rodada {
        const copy: Rodada = Object.assign({}, rodada);
        copy.inicioRodada = this.dateUtils
            .convertDateTimeFromServer(rodada.inicioRodada);
        return copy;
    }

    /**
     * Convert a Rodada to a JSON which can be sent to the server.
     */
    private convert(rodada: Rodada): Rodada {
        const copy: Rodada = Object.assign({}, rodada);

        copy.inicioRodada = this.dateUtils.toDate(rodada.inicioRodada);
        return copy;
    }
}
