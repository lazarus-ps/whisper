import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SampleOfAgentActivity } from './sample-of-agent-activity.model';
import { SampleOfAgentActivityPopupService } from './sample-of-agent-activity-popup.service';
import { SampleOfAgentActivityService } from './sample-of-agent-activity.service';

@Component({
    selector: 'jhi-sample-of-agent-activity-delete-dialog',
    templateUrl: './sample-of-agent-activity-delete-dialog.component.html'
})
export class SampleOfAgentActivityDeleteDialogComponent {

    sampleOfAgentActivity: SampleOfAgentActivity;

    constructor(
        private sampleOfAgentActivityService: SampleOfAgentActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sampleOfAgentActivityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sampleOfAgentActivityListModification',
                content: 'Deleted an sampleOfAgentActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sample-of-agent-activity-delete-popup',
    template: ''
})
export class SampleOfAgentActivityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sampleOfAgentActivityPopupService: SampleOfAgentActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sampleOfAgentActivityPopupService
                .open(SampleOfAgentActivityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
