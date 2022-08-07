import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CtcComponent } from './ctc.component';

describe('CtcComponent', () => {
  let component: CtcComponent;
  let fixture: ComponentFixture<CtcComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CtcComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CtcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
