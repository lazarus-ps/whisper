import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SampleOfCampaignActivity } from './sample-of-campaign-activity.model';
import { SampleOfCampaignActivityPopupService } from './sample-of-campaign-activity-popup.service';
import { SampleOfCampaignActivityService } from './sample-of-campaign-activity.service';

@Component({
    selector: 'jhi-sample-of-campaign-activity-delete-dialog',
    templateUrl: './sample-of-campaign-activity-delete-dialog.component.html'
})
export class SampleOfCampaignActivityDeleteDialogComponent {

    sampleOfCampaignActivity: SampleOfCampaignActivity;

    constructor(
        private sampleOfCampaignActivityService: SampleOfCampaignActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sampleOfCampaignActivityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sampleOfCampaignActivityListModification',
                content: 'Deleted an sampleOfCampaignActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sample-of-campaign-activity-delete-popup',
    template: ''
})
export class SampleOfCampaignActivityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sampleOfCampaignActivityPopupService: SampleOfCampaignActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sampleOfCampaignActivityPopupService
                .open(SampleOfCampaignActivityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
