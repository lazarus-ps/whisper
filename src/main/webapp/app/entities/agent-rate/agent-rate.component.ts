import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { AgentRate } from './agent-rate.model';
import { AgentRateService } from './agent-rate.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-agent-rate',
    templateUrl: './agent-rate.component.html'
})
export class AgentRateComponent implements OnInit, OnDestroy {
agentRates: AgentRate[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private agentRateService: AgentRateService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.agentRateService.query().subscribe(
            (res: ResponseWrapper) => {
                this.agentRates = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAgentRates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AgentRate) {
        return item.id;
    }
    registerChangeInAgentRates() {
        this.eventSubscriber = this.eventManager.subscribe('agentRateListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
