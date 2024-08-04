import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthServiceService } from '../auth-service.service';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-netbanking-payment',
  templateUrl: './netbanking-payment.component.html',
  styleUrls: ['./netbanking-payment.component.css']
})
export class NetbankingPaymentComponent implements OnInit {

  request: NetbankingTransaction = new NetbankingTransaction();
  otp: string = '';
  showOtpField: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private auth: AuthServiceService,
    private router: Router,
    private cartService: CartService  // Inject CartService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.request.amount = +params['amount'] || 0;
    });
  }

  onNetBankingSubmit(): void {
    if (this.request.netbankingUserId && this.request.netbankingPassword && this.request.amount > 0) {
      this.auth.initiateTransactionWithNetBanking(this.request).subscribe((response: any) => {
        if (response.statusCode == 'OK' && response.statusCodeValue == '200' && response.body == 'OTP sent to email. Please verify OTP to complete the transaction.') {
          this.showOtpField = true;
        } else {
          alert('Transaction initiation failed');
        }
      }, error => {
        console.error('Error initiating transaction:', error);
        alert('Transaction initiation failed');
      });
    }
  }

  onNetBankingOtpSubmit(): void {
    this.auth.verifyOtpAndCompleteTransactionWithNetBanking(this.request).subscribe((response: any) => {
      if (response.statusCode == 'OK' && response.statusCodeValue == '200') {
        alert('Transaction successful');
        localStorage.clear();
        this.clearCart();  // Clear the cart after a successful transaction
        this.router.navigate(['/home']);
      } else {
        alert('Transaction failed');
      }
    }, error => {
      console.error('Error completing transaction:', error);
      alert('Transaction failed');
    });
  }

  clearCart(): void {
    this.cartService.clearCart();  // Call clearCart method from CartService
  }
}

export class NetbankingTransaction {
  otp: string = '';
  amount: number = 0.0;
  netbankingUserId: string = '';
  netbankingPassword: string = '';
}
