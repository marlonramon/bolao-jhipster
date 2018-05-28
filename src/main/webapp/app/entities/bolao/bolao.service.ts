import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bolao } from './bolao.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Bolao>;

@Injectable()
export class BolaoService {

    private resourceUrl =  SERVER_API_URL + 'api/bolao';

    constructor(private http: HttpClient) { }

    create(bolao: Bolao): Observable<EntityResponseType> {
        const copy = this.convert(bolao);
        return this.http.post<Bolao>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bolao: Bolao): Observable<EntityResponseType> {
        const copy = this.convert(bolao);
        return this.http.put<Bolao>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Bolao>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Bolao[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bolao[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bolao[]>) => this.convertArrayResponse(res));
    }

    queryByLoggedUser(req?: any): Observable<HttpResponse<Bolao[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bolao[]>("api/user/me/bolao", { params: options, observe: 'response' })
            .map((res: HttpResponse<Bolao[]>) => this.convertArrayResponse(res));
    }



    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Bolao = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Bolao[]>): HttpResponse<Bolao[]> {
        const jsonResponse: Bolao[] = res.body;
        const body: Bolao[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Bolao.
     */
    private convertItemFromServer(bolao: Bolao): Bolao {
        const copy: Bolao = Object.assign({}, bolao);          
        return copy;
    }

    /**
     * Convert a Bolao to a JSON which can be sent to the server.
     */
    private convert(bolao: Bolao): Bolao {
        const copy: Bolao = Object.assign({}, bolao);
        return copy;
    }
}
