import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PrincipleSubscription } from './principle-subscription.model';
import { PrincipleSubscriptionService } from './principle-subscription.service';

@Component({
    selector: 'jhi-principle-subscription-detail',
    templateUrl: './principle-subscription-detail.component.html'
})
export class PrincipleSubscriptionDetailComponent implements OnInit, OnDestroy {

    principleSubscription: PrincipleSubscription;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private principleSubscriptionService: PrincipleSubscriptionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrincipleSubscriptions();
    }

    load(id) {
        this.principleSubscriptionService.find(id).subscribe((principleSubscription) => {
            this.principleSubscription = principleSubscription;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrincipleSubscriptions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'principleSubscriptionListModification',
            (response) => this.load(this.principleSubscription.id)
        );
    }
}
