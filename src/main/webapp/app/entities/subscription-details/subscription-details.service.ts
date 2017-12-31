import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { SubscriptionDetails } from './subscription-details.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SubscriptionDetailsService {

    private resourceUrl = SERVER_API_URL + 'api/subscription-details';

    constructor(private http: Http) { }

    create(subscriptionDetails: SubscriptionDetails): Observable<SubscriptionDetails> {
        const copy = this.convert(subscriptionDetails);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(subscriptionDetails: SubscriptionDetails): Observable<SubscriptionDetails> {
        const copy = this.convert(subscriptionDetails);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SubscriptionDetails> {
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
     * Convert a returned JSON object to SubscriptionDetails.
     */
    private convertItemFromServer(json: any): SubscriptionDetails {
        const entity: SubscriptionDetails = Object.assign(new SubscriptionDetails(), json);
        return entity;
    }

    /**
     * Convert a SubscriptionDetails to a JSON which can be sent to the server.
     */
    private convert(subscriptionDetails: SubscriptionDetails): SubscriptionDetails {
        const copy: SubscriptionDetails = Object.assign({}, subscriptionDetails);
        return copy;
    }
}
