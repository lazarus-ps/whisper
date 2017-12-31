import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { UserData } from './user-data.model';
import { UserDataService } from './user-data.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-data',
    templateUrl: './user-data.component.html'
})
export class UserDataComponent implements OnInit, OnDestroy {
userData: UserData[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private userDataService: UserDataService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.userDataService.query().subscribe(
            (res: ResponseWrapper) => {
                this.userData = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUserData();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: UserData) {
        return item.id;
    }
    registerChangeInUserData() {
        this.eventSubscriber = this.eventManager.subscribe('userDataListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
