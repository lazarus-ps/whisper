import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SampleOfCampaignActivity } from './sample-of-campaign-activity.model';
import { SampleOfCampaignActivityService } from './sample-of-campaign-activity.service';

@Component({
    selector: 'jhi-sample-of-campaign-activity-detail',
    templateUrl: './sample-of-campaign-activity-detail.component.html'
})
export class SampleOfCampaignActivityDetailComponent implements OnInit, OnDestroy {

    sampleOfCampaignActivity: SampleOfCampaignActivity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sampleOfCampaignActivityService: SampleOfCampaignActivityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSampleOfCampaignActivities();
    }

    load(id) {
        this.sampleOfCampaignActivityService.find(id).subscribe((sampleOfCampaignActivity) => {
            this.sampleOfCampaignActivity = sampleOfCampaignActivity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSampleOfCampaignActivities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sampleOfCampaignActivityListModification',
            (response) => this.load(this.sampleOfCampaignActivity.id)
        );
    }
}
