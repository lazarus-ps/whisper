import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhisperSharedModule } from '../../shared';
import {
    SubscriptionDetailsService,
    SubscriptionDetailsPopupService,
    SubscriptionDetailsComponent,
    SubscriptionDetailsDetailComponent,
    SubscriptionDetailsDialogComponent,
    SubscriptionDetailsPopupComponent,
    SubscriptionDetailsDeletePopupComponent,
    SubscriptionDetailsDeleteDialogComponent,
    subscriptionDetailsRoute,
    subscriptionDetailsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...subscriptionDetailsRoute,
    ...subscriptionDetailsPopupRoute,
];

@NgModule({
    imports: [
        WhisperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SubscriptionDetailsComponent,
        SubscriptionDetailsDetailComponent,
        SubscriptionDetailsDialogComponent,
        SubscriptionDetailsDeleteDialogComponent,
        SubscriptionDetailsPopupComponent,
        SubscriptionDetailsDeletePopupComponent,
    ],
    entryComponents: [
        SubscriptionDetailsComponent,
        SubscriptionDetailsDialogComponent,
        SubscriptionDetailsPopupComponent,
        SubscriptionDetailsDeleteDialogComponent,
        SubscriptionDetailsDeletePopupComponent,
    ],
    providers: [
        SubscriptionDetailsService,
        SubscriptionDetailsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperSubscriptionDetailsModule {}
