import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SubscriptionDetails } from './subscription-details.model';
import { SubscriptionDetailsPopupService } from './subscription-details-popup.service';
import { SubscriptionDetailsService } from './subscription-details.service';

@Component({
    selector: 'jhi-subscription-details-delete-dialog',
    templateUrl: './subscription-details-delete-dialog.component.html'
})
export class SubscriptionDetailsDeleteDialogComponent {

    subscriptionDetails: SubscriptionDetails;

    constructor(
        private subscriptionDetailsService: SubscriptionDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.subscriptionDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'subscriptionDetailsListModification',
                content: 'Deleted an subscriptionDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-subscription-details-delete-popup',
    template: ''
})
export class SubscriptionDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subscriptionDetailsPopupService: SubscriptionDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.subscriptionDetailsPopupService
                .open(SubscriptionDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
