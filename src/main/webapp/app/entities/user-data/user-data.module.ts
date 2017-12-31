import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhisperSharedModule } from '../../shared';
import { WhisperAdminModule } from '../../admin/admin.module';
import {
    UserDataService,
    UserDataPopupService,
    UserDataComponent,
    UserDataDetailComponent,
    UserDataDialogComponent,
    UserDataPopupComponent,
    UserDataDeletePopupComponent,
    UserDataDeleteDialogComponent,
    userDataRoute,
    userDataPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userDataRoute,
    ...userDataPopupRoute,
];

@NgModule({
    imports: [
        WhisperSharedModule,
        WhisperAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserDataComponent,
        UserDataDetailComponent,
        UserDataDialogComponent,
        UserDataDeleteDialogComponent,
        UserDataPopupComponent,
        UserDataDeletePopupComponent,
    ],
    entryComponents: [
        UserDataComponent,
        UserDataDialogComponent,
        UserDataPopupComponent,
        UserDataDeleteDialogComponent,
        UserDataDeletePopupComponent,
    ],
    providers: [
        UserDataService,
        UserDataPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhisperUserDataModule {}
