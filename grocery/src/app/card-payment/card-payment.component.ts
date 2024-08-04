import { Component } from '@angular/core';
import { CartService } from '../cart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthServiceService } from '../auth-service.service';

@Component({
  selector: 'app-card-payment',
  templateUrl: './card-payment.component.html',
  styleUrls: ['./card-payment.component.css']
})
export class CardPaymentComponent {

  request :CardTransaction =new CardTransaction();
  otp: string = '';
  showOtpField: boolean = false;

  constructor(private route: ActivatedRoute, private auth: AuthServiceService,private router:Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.request.amount = +params['amount'] || 0;
    });
  }

  onCardSubmit(): void {
    if (this.request.cardNumber && this.request.cardExpiry && this.request.cardCvv && this.request.amount > 0) {
      this.auth.initiateTransactionWithCard(this.request).subscribe((response:any)=>{
        if(response.statusCode=='OK' && response.statusCodeValue=='200' && response.body=='OTP sent to email. Please verify OTP to complete the transaction.'){
        this.showOtpField=true;
        alert('send otp ');
      }else{
        alert('failed');
      }
    })
      }
  }

  onCardOtpSubmit(): void {
    console.log(this.request);
    
    this.auth.verifyOtpAndCompleteTransactionWithCard(this.request).subscribe((response:any)=>{
      if(response.statusCode=='OK' && response.statusCodeValue=='200'){
        alert('transaction successfully completed..');
        localStorage.clear();
        this.router.navigate(['/home']);
      }else{
        alert('failed...!'+response);
      }
    })
  }
}

export class CardTransaction{
  otp:String='';
  amount:number=0.0;
  cardNumber:String='';
  cardExpiry:String='';
  cardCvv:String='';
}
