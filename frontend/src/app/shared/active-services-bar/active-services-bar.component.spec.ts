import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveServicesBarComponent } from './active-services-bar.component';

describe('ActiveServicesBarComponent', () => {
  let component: ActiveServicesBarComponent;
  let fixture: ComponentFixture<ActiveServicesBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActiveServicesBarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActiveServicesBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
