import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectGame2Component } from './select-game2.component';

describe('SelectGame2Component', () => {
  let component: SelectGame2Component;
  let fixture: ComponentFixture<SelectGame2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectGame2Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectGame2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
