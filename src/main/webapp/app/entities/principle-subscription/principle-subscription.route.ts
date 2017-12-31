import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PrincipleSubscriptionComponent } from './principle-subscription.component';
import { PrincipleSubscriptionDetailComponent } from './principle-subscription-detail.component';
import { PrincipleSubscriptionPopupComponent } from './principle-subscription-dialog.component';
import { PrincipleSubscriptionDeletePopupComponent } from './principle-subscription-delete-dialog.component';

export const principleSubscriptionRoute: Routes = [
    {
        path: 'principle-subscription',
        component: PrincipleSubscriptionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principleSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'principle-subscription/:id',
        component: PrincipleSubscriptionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principleSubscription.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const principleSubscriptionPopupRoute: Routes = [
    {
        path: 'principle-subscription-new',
        component: PrincipleSubscriptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principleSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'principle-subscription/:id/edit',
        component: PrincipleSubscriptionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principleSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'principle-subscription/:id/delete',
        component: PrincipleSubscriptionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principleSubscription.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
