import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { SubscriptionDetails } from './subscription-details.model';
import { SubscriptionDetailsService } from './subscription-details.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-subscription-details',
    templateUrl: './subscription-details.component.html'
})
export class SubscriptionDetailsComponent implements OnInit, OnDestroy {
subscriptionDetails: SubscriptionDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private subscriptionDetailsService: SubscriptionDetailsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.subscriptionDetailsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.subscriptionDetails = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSubscriptionDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SubscriptionDetails) {
        return item.id;
    }
    registerChangeInSubscriptionDetails() {
        this.eventSubscriber = this.eventManager.subscribe('subscriptionDetailsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
