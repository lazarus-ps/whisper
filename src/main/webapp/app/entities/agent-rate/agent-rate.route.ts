import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AgentRateComponent } from './agent-rate.component';
import { AgentRateDetailComponent } from './agent-rate-detail.component';
import { AgentRatePopupComponent } from './agent-rate-dialog.component';
import { AgentRateDeletePopupComponent } from './agent-rate-delete-dialog.component';

export const agentRateRoute: Routes = [
    {
        path: 'agent-rate',
        component: AgentRateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.agentRate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agent-rate/:id',
        component: AgentRateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.agentRate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const agentRatePopupRoute: Routes = [
    {
        path: 'agent-rate-new',
        component: AgentRatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.agentRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agent-rate/:id/edit',
        component: AgentRatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.agentRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agent-rate/:id/delete',
        component: AgentRateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'whisperApp.agentRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
