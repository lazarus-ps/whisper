import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { UserData } from './user-data.model';
import { UserDataService } from './user-data.service';

@Component({
    selector: 'jhi-user-data-detail',
    templateUrl: './user-data-detail.component.html'
})
export class UserDataDetailComponent implements OnInit, OnDestroy {

    userData: UserData;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private userDataService: UserDataService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserData();
    }

    load(id) {
        this.userDataService.find(id).subscribe((userData) => {
            this.userData = userData;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserData() {
        this.eventSubscriber = this.eventManager.subscribe(
            'userDataListModification',
            (response) => this.load(this.userData.id)
        );
    }
}
