/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WhisperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CampaignActivityDetailComponent } from '../../../../../../main/webapp/app/entities/campaign-activity/campaign-activity-detail.component';
import { CampaignActivityService } from '../../../../../../main/webapp/app/entities/campaign-activity/campaign-activity.service';
import { CampaignActivity } from '../../../../../../main/webapp/app/entities/campaign-activity/campaign-activity.model';

describe('Component Tests', () => {

    describe('CampaignActivity Management Detail Component', () => {
        let comp: CampaignActivityDetailComponent;
        let fixture: ComponentFixture<CampaignActivityDetailComponent>;
        let service: CampaignActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhisperTestModule],
                declarations: [CampaignActivityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CampaignActivityService,
                    JhiEventManager
                ]
            }).overrideTemplate(CampaignActivityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CampaignActivityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CampaignActivityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CampaignActivity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.campaignActivity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
