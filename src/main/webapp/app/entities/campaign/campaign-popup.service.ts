import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Campaign } from './campaign.model';
import { CampaignService } from './campaign.service';

@Injectable()
export class CampaignPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private campaignService: CampaignService

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
                this.campaignService.find(id).subscribe((campaign) => {
                    if (campaign.startDate) {
                        campaign.startDate = {
                            year: campaign.startDate.getFullYear(),
                            month: campaign.startDate.getMonth() + 1,
                            day: campaign.startDate.getDate()
                        };
                    }
                    if (campaign.endDate) {
                        campaign.endDate = {
                            year: campaign.endDate.getFullYear(),
                            month: campaign.endDate.getMonth() + 1,
                            day: campaign.endDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.campaignModalRef(component, campaign);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.campaignModalRef(component, new Campaign());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    campaignModalRef(component: Component, campaign: Campaign): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.campaign = campaign;
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
