import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PayoutStatusComponentComponent } from './payout-status-component.component';

describe('PayoutStatusComponentComponent', () => {
  let component: PayoutStatusComponentComponent;
  let fixture: ComponentFixture<PayoutStatusComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PayoutStatusComponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PayoutStatusComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
