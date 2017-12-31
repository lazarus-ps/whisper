import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrincipleSubscription } from './principle-subscription.model';
import { PrincipleSubscriptionPopupService } from './principle-subscription-popup.service';
import { PrincipleSubscriptionService } from './principle-subscription.service';
import { SubscriptionDetails, SubscriptionDetailsService } from '../subscription-details';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-principle-subscription-dialog',
    templateUrl: './principle-subscription-dialog.component.html'
})
export class PrincipleSubscriptionDialogComponent implements OnInit {

    principleSubscription: PrincipleSubscription;
    isSaving: boolean;

    subscriptiondetails: SubscriptionDetails[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private principleSubscriptionService: PrincipleSubscriptionService,
        private subscriptionDetailsService: SubscriptionDetailsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.subscriptionDetailsService
            .query({filter: 'principlesubscription-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.principleSubscription.subscriptionDetails || !this.principleSubscription.subscriptionDetails.id) {
                    this.subscriptiondetails = res.json;
                } else {
                    this.subscriptionDetailsService
                        .find(this.principleSubscription.subscriptionDetails.id)
                        .subscribe((subRes: SubscriptionDetails) => {
                            this.subscriptiondetails = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.principleSubscription.id !== undefined) {
            this.subscribeToSaveResponse(
                this.principleSubscriptionService.update(this.principleSubscription));
        } else {
            this.subscribeToSaveResponse(
                this.principleSubscriptionService.create(this.principleSubscription));
        }
    }

    private subscribeToSaveResponse(result: Observable<PrincipleSubscription>) {
        result.subscribe((res: PrincipleSubscription) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PrincipleSubscription) {
        this.eventManager.broadcast({ name: 'principleSubscriptionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSubscriptionDetailsById(index: number, item: SubscriptionDetails) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-principle-subscription-popup',
    template: ''
})
export class PrincipleSubscriptionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private principleSubscriptionPopupService: PrincipleSubscriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.principleSubscriptionPopupService
                    .open(PrincipleSubscriptionDialogComponent as Component, params['id']);
            } else {
                this.principleSubscriptionPopupService
                    .open(PrincipleSubscriptionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
