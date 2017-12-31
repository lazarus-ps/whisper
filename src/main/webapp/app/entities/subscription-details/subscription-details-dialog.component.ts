import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SubscriptionDetails } from './subscription-details.model';
import { SubscriptionDetailsPopupService } from './subscription-details-popup.service';
import { SubscriptionDetailsService } from './subscription-details.service';

@Component({
    selector: 'jhi-subscription-details-dialog',
    templateUrl: './subscription-details-dialog.component.html'
})
export class SubscriptionDetailsDialogComponent implements OnInit {

    subscriptionDetails: SubscriptionDetails;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private subscriptionDetailsService: SubscriptionDetailsService,
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
        if (this.subscriptionDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.subscriptionDetailsService.update(this.subscriptionDetails));
        } else {
            this.subscribeToSaveResponse(
                this.subscriptionDetailsService.create(this.subscriptionDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<SubscriptionDetails>) {
        result.subscribe((res: SubscriptionDetails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SubscriptionDetails) {
        this.eventManager.broadcast({ name: 'subscriptionDetailsListModification', content: 'OK'});
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
    selector: 'jhi-subscription-details-popup',
    template: ''
})
export class SubscriptionDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subscriptionDetailsPopupService: SubscriptionDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.subscriptionDetailsPopupService
                    .open(SubscriptionDetailsDialogComponent as Component, params['id']);
            } else {
                this.subscriptionDetailsPopupService
                    .open(SubscriptionDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
