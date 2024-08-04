import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NetbankingPaymentComponent } from './netbanking-payment.component';

describe('NetbankingPaymentComponent', () => {
  let component: NetbankingPaymentComponent;
  let fixture: ComponentFixture<NetbankingPaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NetbankingPaymentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NetbankingPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
