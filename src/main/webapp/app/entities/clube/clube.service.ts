import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Clube } from './clube.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Clube>;

@Injectable()
export class ClubeService {

    private resourceUrl =  SERVER_API_URL + 'api/clubes';

    constructor(private http: HttpClient) { }

    create(clube: Clube): Observable<EntityResponseType> {
        const copy = this.convert(clube);
        return this.http.post<Clube>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(clube: Clube): Observable<EntityResponseType> {
        const copy = this.convert(clube);
        return this.http.put<Clube>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Clube>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Clube[]>> {
        const options = createRequestOption(req);
        return this.http.get<Clube[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Clube[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Clube = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Clube[]>): HttpResponse<Clube[]> {
        const jsonResponse: Clube[] = res.body;
        const body: Clube[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Clube.
     */
    private convertItemFromServer(clube: Clube): Clube {
        const copy: Clube = Object.assign({}, clube);
        return copy;
    }

    /**
     * Convert a Clube to a JSON which can be sent to the server.
     */
    private convert(clube: Clube): Clube {
        const copy: Clube = Object.assign({}, clube);
        return copy;
    }
}
