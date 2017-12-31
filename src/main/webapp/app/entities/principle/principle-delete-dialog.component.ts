import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Principle } from './principle.model';
import { PrinciplePopupService } from './principle-popup.service';
import { PrincipleService } from './principle.service';

@Component({
    selector: 'jhi-principle-delete-dialog',
    templateUrl: './principle-delete-dialog.component.html'
})
export class PrincipleDeleteDialogComponent {

    principle: Principle;

    constructor(
        private principleService: PrincipleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.principleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'principleListModification',
                content: 'Deleted an principle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-principle-delete-popup',
    template: ''
})
export class PrincipleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private principlePopupService: PrinciplePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.principlePopupService
                .open(PrincipleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
