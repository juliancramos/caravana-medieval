import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectDifficultyComponent } from './select-difficulty.component';

describe('SelectDifficultyComponent', () => {
  let component: SelectDifficultyComponent;
  let fixture: ComponentFixture<SelectDifficultyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectDifficultyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectDifficultyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
