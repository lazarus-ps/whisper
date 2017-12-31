import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AgentRate } from './agent-rate.model';
import { AgentRateService } from './agent-rate.service';

@Component({
    selector: 'jhi-agent-rate-detail',
    templateUrl: './agent-rate-detail.component.html'
})
export class AgentRateDetailComponent implements OnInit, OnDestroy {

    agentRate: AgentRate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private agentRateService: AgentRateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAgentRates();
    }

    load(id) {
        this.agentRateService.find(id).subscribe((agentRate) => {
            this.agentRate = agentRate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAgentRates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'agentRateListModification',
            (response) => this.load(this.agentRate.id)
        );
    }
}
