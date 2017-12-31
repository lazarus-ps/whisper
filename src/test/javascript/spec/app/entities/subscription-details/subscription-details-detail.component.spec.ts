/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WhisperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SubscriptionDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/subscription-details/subscription-details-detail.component';
import { SubscriptionDetailsService } from '../../../../../../main/webapp/app/entities/subscription-details/subscription-details.service';
import { SubscriptionDetails } from '../../../../../../main/webapp/app/entities/subscription-details/subscription-details.model';

describe('Component Tests', () => {

    describe('SubscriptionDetails Management Detail Component', () => {
        let comp: SubscriptionDetailsDetailComponent;
        let fixture: ComponentFixture<SubscriptionDetailsDetailComponent>;
        let service: SubscriptionDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhisperTestModule],
                declarations: [SubscriptionDetailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SubscriptionDetailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(SubscriptionDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubscriptionDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubscriptionDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SubscriptionDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.subscriptionDetails).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
