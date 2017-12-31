/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WhisperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SampleOfAgentActivityDetailComponent } from '../../../../../../main/webapp/app/entities/sample-of-agent-activity/sample-of-agent-activity-detail.component';
import { SampleOfAgentActivityService } from '../../../../../../main/webapp/app/entities/sample-of-agent-activity/sample-of-agent-activity.service';
import { SampleOfAgentActivity } from '../../../../../../main/webapp/app/entities/sample-of-agent-activity/sample-of-agent-activity.model';

describe('Component Tests', () => {

    describe('SampleOfAgentActivity Management Detail Component', () => {
        let comp: SampleOfAgentActivityDetailComponent;
        let fixture: ComponentFixture<SampleOfAgentActivityDetailComponent>;
        let service: SampleOfAgentActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhisperTestModule],
                declarations: [SampleOfAgentActivityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SampleOfAgentActivityService,
                    JhiEventManager
                ]
            }).overrideTemplate(SampleOfAgentActivityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SampleOfAgentActivityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleOfAgentActivityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SampleOfAgentActivity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sampleOfAgentActivity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
