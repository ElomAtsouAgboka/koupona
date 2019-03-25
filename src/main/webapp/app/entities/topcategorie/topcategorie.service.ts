import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITopcategorie } from 'app/shared/model/topcategorie.model';

type EntityResponseType = HttpResponse<ITopcategorie>;
type EntityArrayResponseType = HttpResponse<ITopcategorie[]>;

@Injectable({ providedIn: 'root' })
export class TopcategorieService {
    public resourceUrl = SERVER_API_URL + 'api/topcategories';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/topcategories';

    constructor(protected http: HttpClient) {}

    create(topcategorie: ITopcategorie): Observable<EntityResponseType> {
        return this.http.post<ITopcategorie>(this.resourceUrl, topcategorie, { observe: 'response' });
    }

    update(topcategorie: ITopcategorie): Observable<EntityResponseType> {
        return this.http.put<ITopcategorie>(this.resourceUrl, topcategorie, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITopcategorie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITopcategorie[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITopcategorie[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
