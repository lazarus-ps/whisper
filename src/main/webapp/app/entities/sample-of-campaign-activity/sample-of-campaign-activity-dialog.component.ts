import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SampleOfCampaignActivity } from './sample-of-campaign-activity.model';
import { SampleOfCampaignActivityPopupService } from './sample-of-campaign-activity-popup.service';
import { SampleOfCampaignActivityService } from './sample-of-campaign-activity.service';

@Component({
    selector: 'jhi-sample-of-campaign-activity-dialog',
    templateUrl: './sample-of-campaign-activity-dialog.component.html'
})
export class SampleOfCampaignActivityDialogComponent implements OnInit {

    sampleOfCampaignActivity: SampleOfCampaignActivity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sampleOfCampaignActivityService: SampleOfCampaignActivityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sampleOfCampaignActivity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sampleOfCampaignActivityService.update(this.sampleOfCampaignActivity));
        } else {
            this.subscribeToSaveResponse(
                this.sampleOfCampaignActivityService.create(this.sampleOfCampaignActivity));
        }
    }

    private subscribeToSaveResponse(result: Observable<SampleOfCampaignActivity>) {
        result.subscribe((res: SampleOfCampaignActivity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SampleOfCampaignActivity) {
        this.eventManager.broadcast({ name: 'sampleOfCampaignActivityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-sample-of-campaign-activity-popup',
    template: ''
})
export class SampleOfCampaignActivityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sampleOfCampaignActivityPopupService: SampleOfCampaignActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sampleOfCampaignActivityPopupService
                    .open(SampleOfCampaignActivityDialogComponent as Component, params['id']);
            } else {
                this.sampleOfCampaignActivityPopupService
                    .open(SampleOfCampaignActivityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
