import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhisperSharedModule } from '../../shared';
import {
    SampleOfCampaignActivityService,
    SampleOfCampaignActivityPopupService,
    SampleOfCampaignActivityComponent,
    SampleOfCampaignActivityDetailComponent,
    SampleOfCampaignActivityDialogComponent,
    SampleOfCampaignActivityPopupComponent,
    SampleOfCampaignActivityDeletePopupComponent,
    SampleOfCampaignActivityDeleteDialogComponent,
    sampleOfCampaignActivityRoute,
    sampleOfCampaignActivityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sampleOfCampaignActivityRoute,
    ...sampleOfCampaignActivityPopupRoute,
];

@NgModule({
    imports: [
        WhisperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SampleOfCampaignActivityComponent,
        SampleOfCampaignActivityDetailComponent,
        SampleOfCampaignActivityDialogComponent,
        SampleOfCampaignActivityDeleteDialogComponent,
        SampleOfCampaignActivityPopupComponent,
        SampleOfCampaignActivityDeletePopupComponent,
    ],
    entryComponents: [
        SampleOfCampaignActivityComponent,
        SampleOfCampaignActivityDialogComponent,
        SampleOfCampaignActivityPopupComponent,
        SampleOfCampaignActivityDeleteDialogComponent,
        SampleOfCampaignActivityDeletePopupComponent,
    ],
    providers: [
        SampleOfCampaignActivityService,
        SampleOfCampaignActivityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperSampleOfCampaignActivityModule {}
