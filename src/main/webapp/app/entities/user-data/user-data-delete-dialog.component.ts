import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { UserData } from './user-data.model';
import { UserDataPopupService } from './user-data-popup.service';
import { UserDataService } from './user-data.service';

@Component({
    selector: 'jhi-user-data-delete-dialog',
    templateUrl: './user-data-delete-dialog.component.html'
})
export class UserDataDeleteDialogComponent {

    userData: UserData;

    constructor(
        private userDataService: UserDataService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userDataService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userDataListModification',
                content: 'Deleted an userData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-data-delete-popup',
    template: ''
})
export class UserDataDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userDataPopupService: UserDataPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userDataPopupService
                .open(UserDataDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
