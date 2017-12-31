import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhisperSharedModule } from '../../shared';
import {
    CampaignActivityService,
    CampaignActivityPopupService,
    CampaignActivityComponent,
    CampaignActivityDetailComponent,
    CampaignActivityDialogComponent,
    CampaignActivityPopupComponent,
    CampaignActivityDeletePopupComponent,
    CampaignActivityDeleteDialogComponent,
    campaignActivityRoute,
    campaignActivityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...campaignActivityRoute,
    ...campaignActivityPopupRoute,
];

@NgModule({
    imports: [
        WhisperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CampaignActivityComponent,
        CampaignActivityDetailComponent,
        CampaignActivityDialogComponent,
        CampaignActivityDeleteDialogComponent,
        CampaignActivityPopupComponent,
        CampaignActivityDeletePopupComponent,
    ],
    entryComponents: [
        CampaignActivityComponent,
        CampaignActivityDialogComponent,
        CampaignActivityPopupComponent,
        CampaignActivityDeleteDialogComponent,
        CampaignActivityDeletePopupComponent,
    ],
    providers: [
        CampaignActivityService,
        CampaignActivityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperCampaignActivityModule {}
