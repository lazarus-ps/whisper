import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Principle } from './principle.model';
import { PrincipleService } from './principle.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-principle',
    templateUrl: './principle.component.html'
})
export class PrincipleComponent implements OnInit, OnDestroy {
principles: Principle[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private principleService: PrincipleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.principleService.query().subscribe(
            (res: ResponseWrapper) => {
                this.principles = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrinciples();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Principle) {
        return item.id;
    }
    registerChangeInPrinciples() {
        this.eventSubscriber = this.eventManager.subscribe('principleListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
