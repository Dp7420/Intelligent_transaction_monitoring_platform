import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TransactionRequest } from './upi-payment/upi-payment.component';
import { CardTransaction } from './card-payment/card-payment.component';
import { NetbankingTransaction } from './netbanking-payment/netbanking-payment.component';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private apiUrl = 'http://localhost:8082/api/transactions';

  constructor(private http: HttpClient) {}

  //upi
  initiateTransactionWithUpi(request:TransactionRequest): Observable<object> {
    return this.http.post<object>(`${this.apiUrl}/upi/initiate`,request);
  }

  verifyOtpAndCompleteTransactionWithUpi(request:TransactionRequest): Observable<object> {
    return this.http.post<object>(`${this.apiUrl}/upi/complete`,request);
  }

  //card
  initiateTransactionWithCard(request:CardTransaction): Observable<object> {
    return this.http.post<object>(`${this.apiUrl}/card/initiate`, request);
  }

  verifyOtpAndCompleteTransactionWithCard(request:CardTransaction): Observable<object> {
    return this.http.post<object>(`${this.apiUrl}/card/complete`, request);
  }

  //netbanking
  initiateTransactionWithNetBanking(request:NetbankingTransaction): Observable<object> {
    return this.http.post<object>(`${this.apiUrl}/netbanking/initiate`,request);
  }

  verifyOtpAndCompleteTransactionWithNetBanking(request:NetbankingTransaction): Observable<object> {
    return this.http.post<object>(`${this.apiUrl}/netbanking/complete`, request);
  }
}
