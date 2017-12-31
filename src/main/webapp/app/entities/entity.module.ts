import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WhisperUserDataModule } from './user-data/user-data.module';
import { WhisperPrincipleModule } from './principle/principle.module';
import { WhisperPrincipleSubscriptionModule } from './principle-subscription/principle-subscription.module';
import { WhisperSubscriptionDetailsModule } from './subscription-details/subscription-details.module';
import { WhisperAgentModule } from './agent/agent.module';
import { WhisperSampleOfAgentActivityModule } from './sample-of-agent-activity/sample-of-agent-activity.module';
import { WhisperAgentRateModule } from './agent-rate/agent-rate.module';
import { WhisperAddressModule } from './address/address.module';
import { WhisperCampaignModule } from './campaign/campaign.module';
import { WhisperCampaignActivityModule } from './campaign-activity/campaign-activity.module';
import { WhisperSampleOfCampaignActivityModule } from './sample-of-campaign-activity/sample-of-campaign-activity.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WhisperUserDataModule,
        WhisperPrincipleModule,
        WhisperPrincipleSubscriptionModule,
        WhisperSubscriptionDetailsModule,
        WhisperAgentModule,
        WhisperSampleOfAgentActivityModule,
        WhisperAgentRateModule,
        WhisperAddressModule,
        WhisperCampaignModule,
        WhisperCampaignActivityModule,
        WhisperSampleOfCampaignActivityModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperEntityModule {}
