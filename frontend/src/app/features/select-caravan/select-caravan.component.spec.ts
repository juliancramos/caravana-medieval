import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectCaravanComponent } from './select-caravan.component';

describe('SelectCaravanComponent', () => {
  let component: SelectCaravanComponent;
  let fixture: ComponentFixture<SelectCaravanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectCaravanComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectCaravanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
