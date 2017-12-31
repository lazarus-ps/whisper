/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WhisperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PrincipleDetailComponent } from '../../../../../../main/webapp/app/entities/principle/principle-detail.component';
import { PrincipleService } from '../../../../../../main/webapp/app/entities/principle/principle.service';
import { Principle } from '../../../../../../main/webapp/app/entities/principle/principle.model';

describe('Component Tests', () => {

    describe('Principle Management Detail Component', () => {
        let comp: PrincipleDetailComponent;
        let fixture: ComponentFixture<PrincipleDetailComponent>;
        let service: PrincipleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhisperTestModule],
                declarations: [PrincipleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PrincipleService,
                    JhiEventManager
                ]
            }).overrideTemplate(PrincipleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrincipleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrincipleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Principle(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.principle).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
