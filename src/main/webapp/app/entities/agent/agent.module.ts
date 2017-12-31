import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhisperSharedModule } from '../../shared';
import {
    AgentService,
    AgentPopupService,
    AgentComponent,
    AgentDetailComponent,
    AgentDialogComponent,
    AgentPopupComponent,
    AgentDeletePopupComponent,
    AgentDeleteDialogComponent,
    agentRoute,
    agentPopupRoute,
    AgentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...agentRoute,
    ...agentPopupRoute,
];

@NgModule({
    imports: [
        WhisperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AgentComponent,
        AgentDetailComponent,
        AgentDialogComponent,
        AgentDeleteDialogComponent,
        AgentPopupComponent,
        AgentDeletePopupComponent,
    ],
    entryComponents: [
        AgentComponent,
        AgentDialogComponent,
        AgentPopupComponent,
        AgentDeleteDialogComponent,
        AgentDeletePopupComponent,
    ],
    providers: [
        AgentService,
        AgentPopupService,
        AgentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperAgentModule {}
