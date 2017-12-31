import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { UserData } from './user-data.model';
import { UserDataPopupService } from './user-data-popup.service';
import { UserDataService } from './user-data.service';
import { User, UserService } from '../../shared';
import { Principle, PrincipleService } from '../principle';
import { Agent, AgentService } from '../agent';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-user-data-dialog',
    templateUrl: './user-data-dialog.component.html'
})
export class UserDataDialogComponent implements OnInit {

    userData: UserData;
    isSaving: boolean;

    users: User[];

    principles: Principle[];

    agents: Agent[];
    birthDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private userDataService: UserDataService,
        private userService: UserService,
        private principleService: PrincipleService,
        private agentService: AgentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.principleService
            .query({filter: 'userdata-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.userData.principle || !this.userData.principle.id) {
                    this.principles = res.json;
                } else {
                    this.principleService
                        .find(this.userData.principle.id)
                        .subscribe((subRes: Principle) => {
                            this.principles = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.agentService
            .query({filter: 'userdata-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.userData.agent || !this.userData.agent.id) {
                    this.agents = res.json;
                } else {
                    this.agentService
                        .find(this.userData.agent.id)
                        .subscribe((subRes: Agent) => {
                            this.agents = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.userData.id !== undefined) {
            this.subscribeToSaveResponse(
                this.userDataService.update(this.userData));
        } else {
            this.subscribeToSaveResponse(
                this.userDataService.create(this.userData));
        }
    }

    private subscribeToSaveResponse(result: Observable<UserData>) {
        result.subscribe((res: UserData) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: UserData) {
        this.eventManager.broadcast({ name: 'userDataListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackPrincipleById(index: number, item: Principle) {
        return item.id;
    }

    trackAgentById(index: number, item: Agent) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-data-popup',
    template: ''
})
export class UserDataPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userDataPopupService: UserDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.userDataPopupService
                    .open(UserDataDialogComponent as Component, params['id']);
            } else {
                this.userDataPopupService
                    .open(UserDataDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
