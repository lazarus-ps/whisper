import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { PrincipleSubscription } from './principle-subscription.model';
import { PrincipleSubscriptionService } from './principle-subscription.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-principle-subscription',
    templateUrl: './principle-subscription.component.html'
})
export class PrincipleSubscriptionComponent implements OnInit, OnDestroy {
principleSubscriptions: PrincipleSubscription[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private principleSubscriptionService: PrincipleSubscriptionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.principleSubscriptionService.query().subscribe(
            (res: ResponseWrapper) => {
                this.principleSubscriptions = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrincipleSubscriptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PrincipleSubscription) {
        return item.id;
    }
    registerChangeInPrincipleSubscriptions() {
        this.eventSubscriber = this.eventManager.subscribe('principleSubscriptionListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
