import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { UserData } from './user-data.model';
import { UserDataService } from './user-data.service';

@Injectable()
export class UserDataPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private userDataService: UserDataService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.userDataService.find(id).subscribe((userData) => {
                    if (userData.birthDate) {
                        userData.birthDate = {
                            year: userData.birthDate.getFullYear(),
                            month: userData.birthDate.getMonth() + 1,
                            day: userData.birthDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.userDataModalRef(component, userData);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.userDataModalRef(component, new UserData());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    userDataModalRef(component: Component, userData: UserData): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userData = userData;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
