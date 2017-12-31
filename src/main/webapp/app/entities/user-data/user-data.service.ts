import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { UserData } from './user-data.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserDataService {

    private resourceUrl = SERVER_API_URL + 'api/user-data';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(userData: UserData): Observable<UserData> {
        const copy = this.convert(userData);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(userData: UserData): Observable<UserData> {
        const copy = this.convert(userData);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<UserData> {
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
     * Convert a returned JSON object to UserData.
     */
    private convertItemFromServer(json: any): UserData {
        const entity: UserData = Object.assign(new UserData(), json);
        entity.birthDate = this.dateUtils
            .convertLocalDateFromServer(json.birthDate);
        return entity;
    }

    /**
     * Convert a UserData to a JSON which can be sent to the server.
     */
    private convert(userData: UserData): UserData {
        const copy: UserData = Object.assign({}, userData);
        copy.birthDate = this.dateUtils
            .convertLocalDateToServer(userData.birthDate);
        return copy;
    }
}
