import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Campeonato } from './campeonato.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Campeonato>;

@Injectable()
export class CampeonatoService {

    private resourceUrl =  SERVER_API_URL + 'api/campeonatoes';

    constructor(private http: HttpClient) { }

    create(campeonato: Campeonato): Observable<EntityResponseType> {
        const copy = this.convert(campeonato);
        return this.http.post<Campeonato>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(campeonato: Campeonato): Observable<EntityResponseType> {
        const copy = this.convert(campeonato);
        return this.http.put<Campeonato>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Campeonato>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Campeonato[]>> {
        const options = createRequestOption(req);
        return this.http.get<Campeonato[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Campeonato[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Campeonato = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Campeonato[]>): HttpResponse<Campeonato[]> {
        const jsonResponse: Campeonato[] = res.body;
        const body: Campeonato[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Campeonato.
     */
    private convertItemFromServer(campeonato: Campeonato): Campeonato {
        const copy: Campeonato = Object.assign({}, campeonato);
        return copy;
    }

    /**
     * Convert a Campeonato to a JSON which can be sent to the server.
     */
    private convert(campeonato: Campeonato): Campeonato {
        const copy: Campeonato = Object.assign({}, campeonato);
        return copy;
    }
}
