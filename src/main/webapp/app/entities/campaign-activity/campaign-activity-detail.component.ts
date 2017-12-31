import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CampaignActivity } from './campaign-activity.model';
import { CampaignActivityService } from './campaign-activity.service';

@Component({
    selector: 'jhi-campaign-activity-detail',
    templateUrl: './campaign-activity-detail.component.html'
})
export class CampaignActivityDetailComponent implements OnInit, OnDestroy {

    campaignActivity: CampaignActivity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private campaignActivityService: CampaignActivityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCampaignActivities();
    }

    load(id) {
        this.campaignActivityService.find(id).subscribe((campaignActivity) => {
            this.campaignActivity = campaignActivity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCampaignActivities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'campaignActivityListModification',
            (response) => this.load(this.campaignActivity.id)
        );
    }
}
