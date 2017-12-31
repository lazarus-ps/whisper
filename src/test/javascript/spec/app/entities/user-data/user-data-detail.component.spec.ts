/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { WhisperTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserDataDetailComponent } from '../../../../../../main/webapp/app/entities/user-data/user-data-detail.component';
import { UserDataService } from '../../../../../../main/webapp/app/entities/user-data/user-data.service';
import { UserData } from '../../../../../../main/webapp/app/entities/user-data/user-data.model';

describe('Component Tests', () => {

    describe('UserData Management Detail Component', () => {
        let comp: UserDataDetailComponent;
        let fixture: ComponentFixture<UserDataDetailComponent>;
        let service: UserDataService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WhisperTestModule],
                declarations: [UserDataDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserDataService,
                    JhiEventManager
                ]
            }).overrideTemplate(UserDataDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserDataDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserDataService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserData(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userData).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
