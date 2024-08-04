import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { ProdectComponent } from './prodect/prodect.component';
import { CategoryComponent } from './category/category.component';
import { ContactComponent } from './contact/contact.component';
import { CartComponent } from './cart/cart.component';
import { LoveComponent } from './love/love.component';
import { DealComponent } from './deal/deal.component';
import { FruitsComponent } from './fruits/fruits.component';
import { VegitablesComponent } from './vegitables/vegitables.component';
import { JuiceComponent } from './juice/juice.component';
import { MeatComponent } from './meat/meat.component';
import { HeaderComponent } from './header/header.component';
import { Header1Component } from './header1/header1.component';
import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { TransactionComponent } from './transaction/transaction.component';
import { UpiPaymentComponent } from './upi-payment/upi-payment.component';
import { CardPaymentComponent } from './card-payment/card-payment.component';
import { NetbankingPaymentComponent } from './netbanking-payment/netbanking-payment.component';
import { HttpClientModule } from '@angular/common/http';
import { PaymentsComponent } from './payments/payments.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProdectComponent,
    CategoryComponent,
    ContactComponent,
    CartComponent,
    LoveComponent,
    DealComponent,
    FruitsComponent,
    VegitablesComponent,
    JuiceComponent,
    MeatComponent,
    HeaderComponent,
    Header1Component,
    FooterComponent,
    NavbarComponent,
    LoginComponent,
    SignupComponent,
    TransactionComponent,
    UpiPaymentComponent,
    CardPaymentComponent,
    NetbankingPaymentComponent,
    PaymentsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
