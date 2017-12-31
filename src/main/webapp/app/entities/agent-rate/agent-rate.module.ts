import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhisperSharedModule } from '../../shared';
import {
    AgentRateService,
    AgentRatePopupService,
    AgentRateComponent,
    AgentRateDetailComponent,
    AgentRateDialogComponent,
    AgentRatePopupComponent,
    AgentRateDeletePopupComponent,
    AgentRateDeleteDialogComponent,
    agentRateRoute,
    agentRatePopupRoute,
} from './';

const ENTITY_STATES = [
    ...agentRateRoute,
    ...agentRatePopupRoute,
];

@NgModule({
    imports: [
        WhisperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AgentRateComponent,
        AgentRateDetailComponent,
        AgentRateDialogComponent,
        AgentRateDeleteDialogComponent,
        AgentRatePopupComponent,
        AgentRateDeletePopupComponent,
    ],
    entryComponents: [
        AgentRateComponent,
        AgentRateDialogComponent,
        AgentRatePopupComponent,
        AgentRateDeleteDialogComponent,
        AgentRateDeletePopupComponent,
    ],
    providers: [
        AgentRateService,
        AgentRatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperAgentRateModule {}
