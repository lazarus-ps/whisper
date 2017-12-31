import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrincipleSubscription } from './principle-subscription.model';
import { PrincipleSubscriptionPopupService } from './principle-subscription-popup.service';
import { PrincipleSubscriptionService } from './principle-subscription.service';

@Component({
    selector: 'jhi-principle-subscription-delete-dialog',
    templateUrl: './principle-subscription-delete-dialog.component.html'
})
export class PrincipleSubscriptionDeleteDialogComponent {

    principleSubscription: PrincipleSubscription;

    constructor(
        private principleSubscriptionService: PrincipleSubscriptionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.principleSubscriptionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'principleSubscriptionListModification',
                content: 'Deleted an principleSubscription'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-principle-subscription-delete-popup',
    template: ''
})
export class PrincipleSubscriptionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private principleSubscriptionPopupService: PrincipleSubscriptionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.principleSubscriptionPopupService
                .open(PrincipleSubscriptionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
