import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SampleOfAgentActivityComponent } from './sample-of-agent-activity.component';
import { SampleOfAgentActivityDetailComponent } from './sample-of-agent-activity-detail.component';
import { SampleOfAgentActivityPopupComponent } from './sample-of-agent-activity-dialog.component';
import { SampleOfAgentActivityDeletePopupComponent } from './sample-of-agent-activity-delete-dialog.component';

export const sampleOfAgentActivityRoute: Routes = [
    {
        path: 'sample-of-agent-activity',
        component: SampleOfAgentActivityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfAgentActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sample-of-agent-activity/:id',
        component: SampleOfAgentActivityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfAgentActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sampleOfAgentActivityPopupRoute: Routes = [
    {
        path: 'sample-of-agent-activity-new',
        component: SampleOfAgentActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfAgentActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample-of-agent-activity/:id/edit',
        component: SampleOfAgentActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfAgentActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample-of-agent-activity/:id/delete',
        component: SampleOfAgentActivityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfAgentActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
