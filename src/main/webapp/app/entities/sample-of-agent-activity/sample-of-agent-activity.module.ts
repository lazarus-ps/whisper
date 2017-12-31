import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhisperSharedModule } from '../../shared';
import {
    SampleOfAgentActivityService,
    SampleOfAgentActivityPopupService,
    SampleOfAgentActivityComponent,
    SampleOfAgentActivityDetailComponent,
    SampleOfAgentActivityDialogComponent,
    SampleOfAgentActivityPopupComponent,
    SampleOfAgentActivityDeletePopupComponent,
    SampleOfAgentActivityDeleteDialogComponent,
    sampleOfAgentActivityRoute,
    sampleOfAgentActivityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sampleOfAgentActivityRoute,
    ...sampleOfAgentActivityPopupRoute,
];

@NgModule({
    imports: [
        WhisperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SampleOfAgentActivityComponent,
        SampleOfAgentActivityDetailComponent,
        SampleOfAgentActivityDialogComponent,
        SampleOfAgentActivityDeleteDialogComponent,
        SampleOfAgentActivityPopupComponent,
        SampleOfAgentActivityDeletePopupComponent,
    ],
    entryComponents: [
        SampleOfAgentActivityComponent,
        SampleOfAgentActivityDialogComponent,
        SampleOfAgentActivityPopupComponent,
        SampleOfAgentActivityDeleteDialogComponent,
        SampleOfAgentActivityDeletePopupComponent,
    ],
    providers: [
        SampleOfAgentActivityService,
        SampleOfAgentActivityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperSampleOfAgentActivityModule {}
