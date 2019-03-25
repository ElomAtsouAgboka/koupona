import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuartier } from 'app/shared/model/quartier.model';

type EntityResponseType = HttpResponse<IQuartier>;
type EntityArrayResponseType = HttpResponse<IQuartier[]>;

@Injectable({ providedIn: 'root' })
export class QuartierService {
    public resourceUrl = SERVER_API_URL + 'api/quartiers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/quartiers';

    constructor(protected http: HttpClient) {}

    create(quartier: IQuartier): Observable<EntityResponseType> {
        return this.http.post<IQuartier>(this.resourceUrl, quartier, { observe: 'response' });
    }

    update(quartier: IQuartier): Observable<EntityResponseType> {
        return this.http.put<IQuartier>(this.resourceUrl, quartier, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IQuartier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuartier[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQuartier[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
