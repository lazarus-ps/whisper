import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CampaignActivity } from './campaign-activity.model';
import { CampaignActivityPopupService } from './campaign-activity-popup.service';
import { CampaignActivityService } from './campaign-activity.service';
import { AgentRate, AgentRateService } from '../agent-rate';
import { Campaign, CampaignService } from '../campaign';
import { Agent, AgentService } from '../agent';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-campaign-activity-dialog',
    templateUrl: './campaign-activity-dialog.component.html'
})
export class CampaignActivityDialogComponent implements OnInit {

    campaignActivity: CampaignActivity;
    isSaving: boolean;

    agentrates: AgentRate[];

    campaigns: Campaign[];

    agents: Agent[];
    creationDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private campaignActivityService: CampaignActivityService,
        private agentRateService: AgentRateService,
        private campaignService: CampaignService,
        private agentService: AgentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.agentRateService
            .query({filter: 'campaignactivity-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.campaignActivity.agentRate || !this.campaignActivity.agentRate.id) {
                    this.agentrates = res.json;
                } else {
                    this.agentRateService
                        .find(this.campaignActivity.agentRate.id)
                        .subscribe((subRes: AgentRate) => {
                            this.agentrates = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.campaignService.query()
            .subscribe((res: ResponseWrapper) => { this.campaigns = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.agentService.query()
            .subscribe((res: ResponseWrapper) => { this.agents = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.campaignActivity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.campaignActivityService.update(this.campaignActivity));
        } else {
            this.subscribeToSaveResponse(
                this.campaignActivityService.create(this.campaignActivity));
        }
    }

    private subscribeToSaveResponse(result: Observable<CampaignActivity>) {
        result.subscribe((res: CampaignActivity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CampaignActivity) {
        this.eventManager.broadcast({ name: 'campaignActivityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAgentRateById(index: number, item: AgentRate) {
        return item.id;
    }

    trackCampaignById(index: number, item: Campaign) {
        return item.id;
    }

    trackAgentById(index: number, item: Agent) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-campaign-activity-popup',
    template: ''
})
export class CampaignActivityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private campaignActivityPopupService: CampaignActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.campaignActivityPopupService
                    .open(CampaignActivityDialogComponent as Component, params['id']);
            } else {
                this.campaignActivityPopupService
                    .open(CampaignActivityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
