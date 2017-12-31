import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AgentRate } from './agent-rate.model';
import { AgentRatePopupService } from './agent-rate-popup.service';
import { AgentRateService } from './agent-rate.service';

@Component({
    selector: 'jhi-agent-rate-delete-dialog',
    templateUrl: './agent-rate-delete-dialog.component.html'
})
export class AgentRateDeleteDialogComponent {

    agentRate: AgentRate;

    constructor(
        private agentRateService: AgentRateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.agentRateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'agentRateListModification',
                content: 'Deleted an agentRate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-agent-rate-delete-popup',
    template: ''
})
export class AgentRateDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agentRatePopupService: AgentRatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.agentRatePopupService
                .open(AgentRateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
