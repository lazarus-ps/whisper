import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AgentRate } from './agent-rate.model';
import { AgentRatePopupService } from './agent-rate-popup.service';
import { AgentRateService } from './agent-rate.service';
import { Agent, AgentService } from '../agent';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-agent-rate-dialog',
    templateUrl: './agent-rate-dialog.component.html'
})
export class AgentRateDialogComponent implements OnInit {

    agentRate: AgentRate;
    isSaving: boolean;

    agents: Agent[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private agentRateService: AgentRateService,
        private agentService: AgentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.agentService.query()
            .subscribe((res: ResponseWrapper) => { this.agents = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.agentRate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.agentRateService.update(this.agentRate));
        } else {
            this.subscribeToSaveResponse(
                this.agentRateService.create(this.agentRate));
        }
    }

    private subscribeToSaveResponse(result: Observable<AgentRate>) {
        result.subscribe((res: AgentRate) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AgentRate) {
        this.eventManager.broadcast({ name: 'agentRateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAgentById(index: number, item: Agent) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-agent-rate-popup',
    template: ''
})
export class AgentRatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agentRatePopupService: AgentRatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.agentRatePopupService
                    .open(AgentRateDialogComponent as Component, params['id']);
            } else {
                this.agentRatePopupService
                    .open(AgentRateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
