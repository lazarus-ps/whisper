import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Principle } from './principle.model';
import { PrincipleService } from './principle.service';

@Component({
    selector: 'jhi-principle-detail',
    templateUrl: './principle-detail.component.html'
})
export class PrincipleDetailComponent implements OnInit, OnDestroy {

    principle: Principle;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private principleService: PrincipleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrinciples();
    }

    load(id) {
        this.principleService.find(id).subscribe((principle) => {
            this.principle = principle;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrinciples() {
        this.eventSubscriber = this.eventManager.subscribe(
            'principleListModification',
            (response) => this.load(this.principle.id)
        );
    }
}
