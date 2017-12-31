/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WhisperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SampleOfCampaignActivityDetailComponent } from '../../../../../../main/webapp/app/entities/sample-of-campaign-activity/sample-of-campaign-activity-detail.component';
import { SampleOfCampaignActivityService } from '../../../../../../main/webapp/app/entities/sample-of-campaign-activity/sample-of-campaign-activity.service';
import { SampleOfCampaignActivity } from '../../../../../../main/webapp/app/entities/sample-of-campaign-activity/sample-of-campaign-activity.model';

describe('Component Tests', () => {

    describe('SampleOfCampaignActivity Management Detail Component', () => {
        let comp: SampleOfCampaignActivityDetailComponent;
        let fixture: ComponentFixture<SampleOfCampaignActivityDetailComponent>;
        let service: SampleOfCampaignActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhisperTestModule],
                declarations: [SampleOfCampaignActivityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SampleOfCampaignActivityService,
                    JhiEventManager
                ]
            }).overrideTemplate(SampleOfCampaignActivityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleOfCampaignActivityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleOfCampaignActivityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SampleOfCampaignActivity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sampleOfCampaignActivity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
