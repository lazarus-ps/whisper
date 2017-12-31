import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Principle } from './principle.model';
import { PrinciplePopupService } from './principle-popup.service';
import { PrincipleService } from './principle.service';
import { PrincipleSubscription, PrincipleSubscriptionService } from '../principle-subscription';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-principle-dialog',
    templateUrl: './principle-dialog.component.html'
})
export class PrincipleDialogComponent implements OnInit {

    principle: Principle;
    isSaving: boolean;

    principlesubscriptions: PrincipleSubscription[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private principleService: PrincipleService,
        private principleSubscriptionService: PrincipleSubscriptionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.principleSubscriptionService
            .query({filter: 'principle-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.principle.principleSubscription || !this.principle.principleSubscription.id) {
                    this.principlesubscriptions = res.json;
                } else {
                    this.principleSubscriptionService
                        .find(this.principle.principleSubscription.id)
                        .subscribe((subRes: PrincipleSubscription) => {
                            this.principlesubscriptions = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.principle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.principleService.update(this.principle));
        } else {
            this.subscribeToSaveResponse(
                this.principleService.create(this.principle));
        }
    }

    private subscribeToSaveResponse(result: Observable<Principle>) {
        result.subscribe((res: Principle) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Principle) {
        this.eventManager.broadcast({ name: 'principleListModification', content: 'OK'});
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
    selector: 'jhi-principle-popup',
    template: ''
})
export class PrinciplePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private principlePopupService: PrinciplePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.principlePopupService
                    .open(PrincipleDialogComponent as Component, params['id']);
            } else {
                this.principlePopupService
                    .open(PrincipleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
