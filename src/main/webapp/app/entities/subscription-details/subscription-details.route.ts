import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SubscriptionDetailsComponent } from './subscription-details.component';
import { SubscriptionDetailsDetailComponent } from './subscription-details-detail.component';
import { SubscriptionDetailsPopupComponent } from './subscription-details-dialog.component';
import { SubscriptionDetailsDeletePopupComponent } from './subscription-details-delete-dialog.component';

export const subscriptionDetailsRoute: Routes = [
    {
        path: 'subscription-details',
        component: SubscriptionDetailsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.subscriptionDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'subscription-details/:id',
        component: SubscriptionDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.subscriptionDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subscriptionDetailsPopupRoute: Routes = [
    {
        path: 'subscription-details-new',
        component: SubscriptionDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.subscriptionDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subscription-details/:id/edit',
        component: SubscriptionDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.subscriptionDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subscription-details/:id/delete',
        component: SubscriptionDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.subscriptionDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
