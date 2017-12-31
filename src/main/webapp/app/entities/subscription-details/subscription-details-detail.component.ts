import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SubscriptionDetails } from './subscription-details.model';
import { SubscriptionDetailsService } from './subscription-details.service';

@Component({
    selector: 'jhi-subscription-details-detail',
    templateUrl: './subscription-details-detail.component.html'
})
export class SubscriptionDetailsDetailComponent implements OnInit, OnDestroy {

    subscriptionDetails: SubscriptionDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private subscriptionDetailsService: SubscriptionDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSubscriptionDetails();
    }

    load(id) {
        this.subscriptionDetailsService.find(id).subscribe((subscriptionDetails) => {
            this.subscriptionDetails = subscriptionDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSubscriptionDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'subscriptionDetailsListModification',
            (response) => this.load(this.subscriptionDetails.id)
        );
    }
}
