import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SampleOfAgentActivity } from './sample-of-agent-activity.model';
import { SampleOfAgentActivityPopupService } from './sample-of-agent-activity-popup.service';
import { SampleOfAgentActivityService } from './sample-of-agent-activity.service';

@Component({
    selector: 'jhi-sample-of-agent-activity-dialog',
    templateUrl: './sample-of-agent-activity-dialog.component.html'
})
export class SampleOfAgentActivityDialogComponent implements OnInit {

    sampleOfAgentActivity: SampleOfAgentActivity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sampleOfAgentActivityService: SampleOfAgentActivityService,
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
        if (this.sampleOfAgentActivity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sampleOfAgentActivityService.update(this.sampleOfAgentActivity));
        } else {
            this.subscribeToSaveResponse(
                this.sampleOfAgentActivityService.create(this.sampleOfAgentActivity));
        }
    }

    private subscribeToSaveResponse(result: Observable<SampleOfAgentActivity>) {
        result.subscribe((res: SampleOfAgentActivity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SampleOfAgentActivity) {
        this.eventManager.broadcast({ name: 'sampleOfAgentActivityListModification', content: 'OK'});
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
    selector: 'jhi-sample-of-agent-activity-popup',
    template: ''
})
export class SampleOfAgentActivityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sampleOfAgentActivityPopupService: SampleOfAgentActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sampleOfAgentActivityPopupService
                    .open(SampleOfAgentActivityDialogComponent as Component, params['id']);
            } else {
                this.sampleOfAgentActivityPopupService
                    .open(SampleOfAgentActivityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
