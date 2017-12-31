import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CampaignActivity } from './campaign-activity.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CampaignActivityService {

    private resourceUrl = SERVER_API_URL + 'api/campaign-activities';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(campaignActivity: CampaignActivity): Observable<CampaignActivity> {
        const copy = this.convert(campaignActivity);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(campaignActivity: CampaignActivity): Observable<CampaignActivity> {
        const copy = this.convert(campaignActivity);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CampaignActivity> {
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
     * Convert a returned JSON object to CampaignActivity.
     */
    private convertItemFromServer(json: any): CampaignActivity {
        const entity: CampaignActivity = Object.assign(new CampaignActivity(), json);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        return entity;
    }

    /**
     * Convert a CampaignActivity to a JSON which can be sent to the server.
     */
    private convert(campaignActivity: CampaignActivity): CampaignActivity {
        const copy: CampaignActivity = Object.assign({}, campaignActivity);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(campaignActivity.creationDate);
        return copy;
    }
}
