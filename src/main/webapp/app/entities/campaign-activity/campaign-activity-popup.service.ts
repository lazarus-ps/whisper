import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CampaignActivity } from './campaign-activity.model';
import { CampaignActivityService } from './campaign-activity.service';

@Injectable()
export class CampaignActivityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private campaignActivityService: CampaignActivityService

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
                this.campaignActivityService.find(id).subscribe((campaignActivity) => {
                    if (campaignActivity.creationDate) {
                        campaignActivity.creationDate = {
                            year: campaignActivity.creationDate.getFullYear(),
                            month: campaignActivity.creationDate.getMonth() + 1,
                            day: campaignActivity.creationDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.campaignActivityModalRef(component, campaignActivity);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.campaignActivityModalRef(component, new CampaignActivity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    campaignActivityModalRef(component: Component, campaignActivity: CampaignActivity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.campaignActivity = campaignActivity;
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
