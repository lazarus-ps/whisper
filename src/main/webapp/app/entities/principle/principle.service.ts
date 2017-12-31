import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Principle } from './principle.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PrincipleService {

    private resourceUrl = SERVER_API_URL + 'api/principles';

    constructor(private http: Http) { }

    create(principle: Principle): Observable<Principle> {
        const copy = this.convert(principle);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(principle: Principle): Observable<Principle> {
        const copy = this.convert(principle);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Principle> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Principle.
     */
    private convertItemFromServer(json: any): Principle {
        const entity: Principle = Object.assign(new Principle(), json);
        return entity;
    }

    /**
     * Convert a Principle to a JSON which can be sent to the server.
     */
    private convert(principle: Principle): Principle {
        const copy: Principle = Object.assign({}, principle);
        return copy;
    }
}
