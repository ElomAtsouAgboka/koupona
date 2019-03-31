import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISousmenu } from 'app/shared/model/sousmenu.model';

type EntityResponseType = HttpResponse<ISousmenu>;
type EntityArrayResponseType = HttpResponse<ISousmenu[]>;

@Injectable({ providedIn: 'root' })
export class SousmenuService {
    public resourceUrl = SERVER_API_URL + 'api/sousmenus';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/sousmenus';

    constructor(protected http: HttpClient) {}

    create(sousmenu: ISousmenu): Observable<EntityResponseType> {
        return this.http.post<ISousmenu>(this.resourceUrl, sousmenu, { observe: 'response' });
    }

    update(sousmenu: ISousmenu): Observable<EntityResponseType> {
        return this.http.put<ISousmenu>(this.resourceUrl, sousmenu, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISousmenu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISousmenu[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISousmenu[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
