import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { SampleOfAgentActivity } from './sample-of-agent-activity.model';
import { SampleOfAgentActivityService } from './sample-of-agent-activity.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sample-of-agent-activity',
    templateUrl: './sample-of-agent-activity.component.html'
})
export class SampleOfAgentActivityComponent implements OnInit, OnDestroy {
sampleOfAgentActivities: SampleOfAgentActivity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sampleOfAgentActivityService: SampleOfAgentActivityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sampleOfAgentActivityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sampleOfAgentActivities = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSampleOfAgentActivities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SampleOfAgentActivity) {
        return item.id;
    }
    registerChangeInSampleOfAgentActivities() {
        this.eventSubscriber = this.eventManager.subscribe('sampleOfAgentActivityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
