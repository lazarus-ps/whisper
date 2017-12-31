import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserDataComponent } from './user-data.component';
import { UserDataDetailComponent } from './user-data-detail.component';
import { UserDataPopupComponent } from './user-data-dialog.component';
import { UserDataDeletePopupComponent } from './user-data-delete-dialog.component';

export const userDataRoute: Routes = [
    {
        path: 'user-data',
        component: UserDataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.userData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-data/:id',
        component: UserDataDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.userData.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userDataPopupRoute: Routes = [
    {
        path: 'user-data-new',
        component: UserDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.userData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-data/:id/edit',
        component: UserDataPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.userData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-data/:id/delete',
        component: UserDataDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.userData.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
