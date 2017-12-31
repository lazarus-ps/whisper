import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PrincipleSubscription } from './principle-subscription.model';
import { PrincipleSubscriptionService } from './principle-subscription.service';

@Injectable()
export class PrincipleSubscriptionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private principleSubscriptionService: PrincipleSubscriptionService

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
                this.principleSubscriptionService.find(id).subscribe((principleSubscription) => {
                    if (principleSubscription.startDate) {
                        principleSubscription.startDate = {
                            year: principleSubscription.startDate.getFullYear(),
                            month: principleSubscription.startDate.getMonth() + 1,
                            day: principleSubscription.startDate.getDate()
                        };
                    }
                    if (principleSubscription.endDate) {
                        principleSubscription.endDate = {
                            year: principleSubscription.endDate.getFullYear(),
                            month: principleSubscription.endDate.getMonth() + 1,
                            day: principleSubscription.endDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.principleSubscriptionModalRef(component, principleSubscription);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.principleSubscriptionModalRef(component, new PrincipleSubscription());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    principleSubscriptionModalRef(component: Component, principleSubscription: PrincipleSubscription): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.principleSubscription = principleSubscription;
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
