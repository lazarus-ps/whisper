/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WhisperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AgentRateDetailComponent } from '../../../../../../main/webapp/app/entities/agent-rate/agent-rate-detail.component';
import { AgentRateService } from '../../../../../../main/webapp/app/entities/agent-rate/agent-rate.service';
import { AgentRate } from '../../../../../../main/webapp/app/entities/agent-rate/agent-rate.model';

describe('Component Tests', () => {

    describe('AgentRate Management Detail Component', () => {
        let comp: AgentRateDetailComponent;
        let fixture: ComponentFixture<AgentRateDetailComponent>;
        let service: AgentRateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhisperTestModule],
                declarations: [AgentRateDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AgentRateService,
                    JhiEventManager
                ]
            }).overrideTemplate(AgentRateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AgentRateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AgentRateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AgentRate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.agentRate).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
