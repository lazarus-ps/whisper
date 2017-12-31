import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Campaign } from './campaign.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CampaignService {

    private resourceUrl = SERVER_API_URL + 'api/campaigns';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(campaign: Campaign): Observable<Campaign> {
        const copy = this.convert(campaign);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(campaign: Campaign): Observable<Campaign> {
        const copy = this.convert(campaign);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Campaign> {
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
     * Convert a returned JSON object to Campaign.
     */
    private convertItemFromServer(json: any): Campaign {
        const entity: Campaign = Object.assign(new Campaign(), json);
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(json.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(json.endDate);
        return entity;
    }

    /**
     * Convert a Campaign to a JSON which can be sent to the server.
     */
    private convert(campaign: Campaign): Campaign {
        const copy: Campaign = Object.assign({}, campaign);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(campaign.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(campaign.endDate);
        return copy;
    }
}
