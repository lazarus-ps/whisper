import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SampleOfAgentActivity } from './sample-of-agent-activity.model';
import { SampleOfAgentActivityService } from './sample-of-agent-activity.service';

@Injectable()
export class SampleOfAgentActivityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private sampleOfAgentActivityService: SampleOfAgentActivityService

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
                this.sampleOfAgentActivityService.find(id).subscribe((sampleOfAgentActivity) => {
                    this.ngbModalRef = this.sampleOfAgentActivityModalRef(component, sampleOfAgentActivity);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.sampleOfAgentActivityModalRef(component, new SampleOfAgentActivity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    sampleOfAgentActivityModalRef(component: Component, sampleOfAgentActivity: SampleOfAgentActivity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sampleOfAgentActivity = sampleOfAgentActivity;
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
