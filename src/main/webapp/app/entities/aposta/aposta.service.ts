import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Aposta } from './aposta.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Aposta>;

@Injectable()
export class ApostaService {

    private resourceUrl =  SERVER_API_URL + 'api/apostas';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(aposta: Aposta): Observable<EntityResponseType> {
        const copy = this.convert(aposta);
        return this.http.post<Aposta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(aposta: Aposta): Observable<EntityResponseType> {
        const copy = this.convert(aposta);
        return this.http.put<Aposta>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Aposta>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Aposta[]>> {
        const options = createRequestOption(req);
        return this.http.get<Aposta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Aposta[]>) => this.convertArrayResponse(res));
    }

    queryByLoggedUserAndRodada(idRodada: number): Observable<HttpResponse<Aposta[]>> {
        return this.http.get<Aposta[]>(`/api/user/me/rodada/${idRodada}/apostas`, {observe: 'response' })
            .map((res: HttpResponse<Aposta[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Aposta = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Aposta[]>): HttpResponse<Aposta[]> {
        const jsonResponse: Aposta[] = res.body;
        const body: Aposta[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Aposta.
     */
    private convertItemFromServer(aposta: Aposta): Aposta {
        const copy: Aposta = Object.assign({}, aposta);
        copy.dataAposta = this.dateUtils
            .convertDateTimeFromServer(aposta.dataAposta);
        return copy;
    }

    /**
     * Convert a Aposta to a JSON which can be sent to the server.
     */
    private convert(aposta: Aposta): Aposta {
        const copy: Aposta = Object.assign({}, aposta);

        return copy;
    }
}
