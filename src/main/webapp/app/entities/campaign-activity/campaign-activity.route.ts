import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CampaignActivityComponent } from './campaign-activity.component';
import { CampaignActivityDetailComponent } from './campaign-activity-detail.component';
import { CampaignActivityPopupComponent } from './campaign-activity-dialog.component';
import { CampaignActivityDeletePopupComponent } from './campaign-activity-delete-dialog.component';

export const campaignActivityRoute: Routes = [
    {
        path: 'campaign-activity',
        component: CampaignActivityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.campaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'campaign-activity/:id',
        component: CampaignActivityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.campaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const campaignActivityPopupRoute: Routes = [
    {
        path: 'campaign-activity-new',
        component: CampaignActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.campaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'campaign-activity/:id/edit',
        component: CampaignActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.campaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'campaign-activity/:id/delete',
        component: CampaignActivityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.campaignActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
