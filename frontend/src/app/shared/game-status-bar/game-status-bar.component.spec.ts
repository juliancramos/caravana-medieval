import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameStatusBarComponent } from './game-status-bar.component';

describe('GameStatusBarComponent', () => {
  let component: GameStatusBarComponent;
  let fixture: ComponentFixture<GameStatusBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GameStatusBarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GameStatusBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
