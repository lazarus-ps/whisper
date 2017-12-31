import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PrincipleSubscription } from './principle-subscription.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PrincipleSubscriptionService {

    private resourceUrl = SERVER_API_URL + 'api/principle-subscriptions';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(principleSubscription: PrincipleSubscription): Observable<PrincipleSubscription> {
        const copy = this.convert(principleSubscription);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(principleSubscription: PrincipleSubscription): Observable<PrincipleSubscription> {
        const copy = this.convert(principleSubscription);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PrincipleSubscription> {
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
     * Convert a returned JSON object to PrincipleSubscription.
     */
    private convertItemFromServer(json: any): PrincipleSubscription {
        const entity: PrincipleSubscription = Object.assign(new PrincipleSubscription(), json);
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(json.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(json.endDate);
        return entity;
    }

    /**
     * Convert a PrincipleSubscription to a JSON which can be sent to the server.
     */
    private convert(principleSubscription: PrincipleSubscription): PrincipleSubscription {
        const copy: PrincipleSubscription = Object.assign({}, principleSubscription);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(principleSubscription.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(principleSubscription.endDate);
        return copy;
    }
}
