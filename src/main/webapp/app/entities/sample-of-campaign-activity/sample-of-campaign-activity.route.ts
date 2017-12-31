import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SampleOfCampaignActivityComponent } from './sample-of-campaign-activity.component';
import { SampleOfCampaignActivityDetailComponent } from './sample-of-campaign-activity-detail.component';
import { SampleOfCampaignActivityPopupComponent } from './sample-of-campaign-activity-dialog.component';
import { SampleOfCampaignActivityDeletePopupComponent } from './sample-of-campaign-activity-delete-dialog.component';

export const sampleOfCampaignActivityRoute: Routes = [
    {
        path: 'sample-of-campaign-activity',
        component: SampleOfCampaignActivityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfCampaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sample-of-campaign-activity/:id',
        component: SampleOfCampaignActivityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfCampaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sampleOfCampaignActivityPopupRoute: Routes = [
    {
        path: 'sample-of-campaign-activity-new',
        component: SampleOfCampaignActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfCampaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample-of-campaign-activity/:id/edit',
        component: SampleOfCampaignActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfCampaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sample-of-campaign-activity/:id/delete',
        component: SampleOfCampaignActivityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.sampleOfCampaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
