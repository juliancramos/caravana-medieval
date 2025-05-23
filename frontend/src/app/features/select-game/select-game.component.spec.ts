import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectGameComponent } from './select-game.component';

describe('SelectGame2Component', () => {
  let component: SelectGameComponent;
  let fixture: ComponentFixture<SelectGameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectGameComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectGameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
