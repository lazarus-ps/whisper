import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { CampaignActivity } from './campaign-activity.model';
import { CampaignActivityService } from './campaign-activity.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-campaign-activity',
    templateUrl: './campaign-activity.component.html'
})
export class CampaignActivityComponent implements OnInit, OnDestroy {
campaignActivities: CampaignActivity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private campaignActivityService: CampaignActivityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.campaignActivityService.query().subscribe(
            (res: ResponseWrapper) => {
                this.campaignActivities = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCampaignActivities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CampaignActivity) {
        return item.id;
    }
    registerChangeInCampaignActivities() {
        this.eventSubscriber = this.eventManager.subscribe('campaignActivityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
