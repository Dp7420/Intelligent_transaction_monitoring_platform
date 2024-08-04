import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthServiceService } from '../auth-service.service';

@Component({
  selector: 'app-upi-payment',
  templateUrl: './upi-payment.component.html',
  styleUrls: ['./upi-payment.component.css']
})
export class UpiPaymentComponent implements OnInit {

  request:TransactionRequest=new TransactionRequest();
  pin: string = '';  // Updated to match HTML
  totalAmount: number = 0;  // Use number type for amount
  showOtpField: boolean = false;  // Use boolean type
  otp: string = '';  // Use string type
  amount: number = 0;

  constructor(private route: ActivatedRoute, private auth: AuthServiceService,private router:Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.request.amount = +params['amount'] || 0;
      this.totalAmount = this.amount;  // Set totalAmount as number
    });
  }

  onSubmit(): void {
    console.log(this.request);
    
    this.auth.initiateTransactionWithUpi(this.request).subscribe((response:any)=>{
      if(response.statusCode=='OK' && response.statusCodeValue=='200' && response.body=='OTP sent to email. Please verify OTP to complete the transaction.'){
        this.showOtpField=true;
        alert('send otp ');
      }else{
        alert('failed');
      }
    })
  }

  onOtpSubmit(): void {
    if (this.otp) {
      this.request.otp=this.otp;
      this.auth.verifyOtpAndCompleteTransactionWithUpi(this.request).subscribe((response:any)=>{
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
}

export class TransactionRequest{
  upiId:String='';
  upiPassword:String='';
  amount:number=0.0;
  otp:String='';
}