import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhisperSharedModule } from '../../shared';
import {
    PrincipleSubscriptionService,
    PrincipleSubscriptionPopupService,
    PrincipleSubscriptionComponent,
    PrincipleSubscriptionDetailComponent,
    PrincipleSubscriptionDialogComponent,
    PrincipleSubscriptionPopupComponent,
    PrincipleSubscriptionDeletePopupComponent,
    PrincipleSubscriptionDeleteDialogComponent,
    principleSubscriptionRoute,
    principleSubscriptionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...principleSubscriptionRoute,
    ...principleSubscriptionPopupRoute,
];

@NgModule({
    imports: [
        WhisperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PrincipleSubscriptionComponent,
        PrincipleSubscriptionDetailComponent,
        PrincipleSubscriptionDialogComponent,
        PrincipleSubscriptionDeleteDialogComponent,
        PrincipleSubscriptionPopupComponent,
        PrincipleSubscriptionDeletePopupComponent,
    ],
    entryComponents: [
        PrincipleSubscriptionComponent,
        PrincipleSubscriptionDialogComponent,
        PrincipleSubscriptionPopupComponent,
        PrincipleSubscriptionDeleteDialogComponent,
        PrincipleSubscriptionDeletePopupComponent,
    ],
    providers: [
        PrincipleSubscriptionService,
        PrincipleSubscriptionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperPrincipleSubscriptionModule {}
