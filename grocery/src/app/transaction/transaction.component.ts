import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit{
  selectedPaymentMethod = 'credit-card';
  name: string = '';
  email: string = '';
  amount: number = 0;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.amount = +params['amount'] || 0;
    });
  }

  get totalAmount(): string {
    return `$${this.amount.toFixed(2)}`;
  }

  onSubmit(): void {
    const queryParams = { amount: this.amount };
    
    if (this.selectedPaymentMethod === 'upi') {
      this.router.navigate(['/upi'], { queryParams });
    } else if (this.selectedPaymentMethod === 'netbanking') {
      this.router.navigate(['/netbanking'], { queryParams });
    } else if (this.selectedPaymentMethod === 'card') {
      this.router.navigate(['/card'], { queryParams });
    }

    // alert(`Payment method: ${this.selectedPaymentMethod}\nName: ${this.name}\nEmail: ${this.email}\nAmount: ${this.totalAmount}`);
  }
}
