import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISouscategorie } from 'app/shared/model/souscategorie.model';

type EntityResponseType = HttpResponse<ISouscategorie>;
type EntityArrayResponseType = HttpResponse<ISouscategorie[]>;

@Injectable({ providedIn: 'root' })
export class SouscategorieService {
    public resourceUrl = SERVER_API_URL + 'api/souscategories';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/souscategories';

    constructor(protected http: HttpClient) {}

    create(souscategorie: ISouscategorie): Observable<EntityResponseType> {
        return this.http.post<ISouscategorie>(this.resourceUrl, souscategorie, { observe: 'response' });
    }

    update(souscategorie: ISouscategorie): Observable<EntityResponseType> {
        return this.http.put<ISouscategorie>(this.resourceUrl, souscategorie, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISouscategorie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISouscategorie[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISouscategorie[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
