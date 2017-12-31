import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhisperSharedModule } from '../../shared';
import {
    PrincipleService,
    PrinciplePopupService,
    PrincipleComponent,
    PrincipleDetailComponent,
    PrincipleDialogComponent,
    PrinciplePopupComponent,
    PrincipleDeletePopupComponent,
    PrincipleDeleteDialogComponent,
    principleRoute,
    principlePopupRoute,
} from './';

const ENTITY_STATES = [
    ...principleRoute,
    ...principlePopupRoute,
];

@NgModule({
    imports: [
        WhisperSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PrincipleComponent,
        PrincipleDetailComponent,
        PrincipleDialogComponent,
        PrincipleDeleteDialogComponent,
        PrinciplePopupComponent,
        PrincipleDeletePopupComponent,
    ],
    entryComponents: [
        PrincipleComponent,
        PrincipleDialogComponent,
        PrinciplePopupComponent,
        PrincipleDeleteDialogComponent,
        PrincipleDeletePopupComponent,
    ],
    providers: [
        PrincipleService,
        PrinciplePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperPrincipleModule {}
