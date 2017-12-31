import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CampaignActivity } from './campaign-activity.model';
import { CampaignActivityPopupService } from './campaign-activity-popup.service';
import { CampaignActivityService } from './campaign-activity.service';

@Component({
    selector: 'jhi-campaign-activity-delete-dialog',
    templateUrl: './campaign-activity-delete-dialog.component.html'
})
export class CampaignActivityDeleteDialogComponent {

    campaignActivity: CampaignActivity;

    constructor(
        private campaignActivityService: CampaignActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.campaignActivityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'campaignActivityListModification',
                content: 'Deleted an campaignActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-campaign-activity-delete-popup',
    template: ''
})
export class CampaignActivityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private campaignActivityPopupService: CampaignActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.campaignActivityPopupService
                .open(CampaignActivityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
