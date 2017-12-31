import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { SampleOfAgentActivity } from './sample-of-agent-activity.model';
import { SampleOfAgentActivityService } from './sample-of-agent-activity.service';

@Component({
    selector: 'jhi-sample-of-agent-activity-detail',
    templateUrl: './sample-of-agent-activity-detail.component.html'
})
export class SampleOfAgentActivityDetailComponent implements OnInit, OnDestroy {

    sampleOfAgentActivity: SampleOfAgentActivity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sampleOfAgentActivityService: SampleOfAgentActivityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSampleOfAgentActivities();
    }

    load(id) {
        this.sampleOfAgentActivityService.find(id).subscribe((sampleOfAgentActivity) => {
            this.sampleOfAgentActivity = sampleOfAgentActivity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSampleOfAgentActivities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sampleOfAgentActivityListModification',
            (response) => this.load(this.sampleOfAgentActivity.id)
        );
    }
}
