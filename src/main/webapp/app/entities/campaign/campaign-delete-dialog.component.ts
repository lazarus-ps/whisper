import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Campaign } from './campaign.model';
import { CampaignPopupService } from './campaign-popup.service';
import { CampaignService } from './campaign.service';

@Component({
    selector: 'jhi-campaign-delete-dialog',
    templateUrl: './campaign-delete-dialog.component.html'
})
export class CampaignDeleteDialogComponent {

    campaign: Campaign;

    constructor(
        private campaignService: CampaignService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.campaignService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'campaignListModification',
                content: 'Deleted an campaign'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-campaign-delete-popup',
    template: ''
})
export class CampaignDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private campaignPopupService: CampaignPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.campaignPopupService
                .open(CampaignDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
