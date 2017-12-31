/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WhisperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PrincipleSubscriptionDetailComponent } from '../../../../../../main/webapp/app/entities/principle-subscription/principle-subscription-detail.component';
import { PrincipleSubscriptionService } from '../../../../../../main/webapp/app/entities/principle-subscription/principle-subscription.service';
import { PrincipleSubscription } from '../../../../../../main/webapp/app/entities/principle-subscription/principle-subscription.model';

describe('Component Tests', () => {

    describe('PrincipleSubscription Management Detail Component', () => {
        let comp: PrincipleSubscriptionDetailComponent;
        let fixture: ComponentFixture<PrincipleSubscriptionDetailComponent>;
        let service: PrincipleSubscriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhisperTestModule],
                declarations: [PrincipleSubscriptionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PrincipleSubscriptionService,
                    JhiEventManager
                ]
            }).overrideTemplate(PrincipleSubscriptionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrincipleSubscriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrincipleSubscriptionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PrincipleSubscription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.principleSubscription).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
