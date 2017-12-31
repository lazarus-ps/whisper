import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Campaign } from './campaign.model';
import { CampaignPopupService } from './campaign-popup.service';
import { CampaignService } from './campaign.service';
import { PrincipleSubscription, PrincipleSubscriptionService } from '../principle-subscription';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-campaign-dialog',
    templateUrl: './campaign-dialog.component.html'
})
export class CampaignDialogComponent implements OnInit {

    campaign: Campaign;
    isSaving: boolean;

    principlesubscriptions: PrincipleSubscription[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private campaignService: CampaignService,
        private principleSubscriptionService: PrincipleSubscriptionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.principleSubscriptionService.query()
            .subscribe((res: ResponseWrapper) => { this.principlesubscriptions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.campaign.id !== undefined) {
            this.subscribeToSaveResponse(
                this.campaignService.update(this.campaign));
        } else {
            this.subscribeToSaveResponse(
                this.campaignService.create(this.campaign));
        }
    }

    private subscribeToSaveResponse(result: Observable<Campaign>) {
        result.subscribe((res: Campaign) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Campaign) {
        this.eventManager.broadcast({ name: 'campaignListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPrincipleSubscriptionById(index: number, item: PrincipleSubscription) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-campaign-popup',
    template: ''
})
export class CampaignPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private campaignPopupService: CampaignPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.campaignPopupService
                    .open(CampaignDialogComponent as Component, params['id']);
            } else {
                this.campaignPopupService
                    .open(CampaignDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
