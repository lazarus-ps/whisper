import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PrincipleComponent } from './principle.component';
import { PrincipleDetailComponent } from './principle-detail.component';
import { PrinciplePopupComponent } from './principle-dialog.component';
import { PrincipleDeletePopupComponent } from './principle-delete-dialog.component';

export const principleRoute: Routes = [
    {
        path: 'principle',
        component: PrincipleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'principle/:id',
        component: PrincipleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const principlePopupRoute: Routes = [
    {
        path: 'principle-new',
        component: PrinciplePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'principle/:id/edit',
        component: PrinciplePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'principle/:id/delete',
        component: PrincipleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.principle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
