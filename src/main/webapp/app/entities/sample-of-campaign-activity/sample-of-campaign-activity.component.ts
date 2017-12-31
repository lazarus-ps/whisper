import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { SampleOfCampaignActivity } from './sample-of-campaign-activity.model';
import { SampleOfCampaignActivityService } from './sample-of-campaign-activity.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sample-of-campaign-activity',
    templateUrl: './sample-of-campaign-activity.component.html'
})
export class SampleOfCampaignActivityComponent implements OnInit, OnDestroy {
sampleOfCampaignActivities: SampleOfCampaignActivity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sampleOfCampaignActivityService: SampleOfCampaignActivityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sampleOfCampaignActivityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sampleOfCampaignActivities = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSampleOfCampaignActivities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SampleOfCampaignActivity) {
        return item.id;
    }
    registerChangeInSampleOfCampaignActivities() {
        this.eventSubscriber = this.eventManager.subscribe('sampleOfCampaignActivityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
