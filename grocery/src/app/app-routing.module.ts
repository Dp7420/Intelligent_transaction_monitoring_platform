import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CategoryComponent } from './category/category.component';
import { CartComponent } from './cart/cart.component';
import { ProdectComponent } from './prodect/prodect.component';
import { DealComponent } from './deal/deal.component';
import { ContactComponent } from './contact/contact.component';
import { FruitsComponent } from './fruits/fruits.component';
import { JuiceComponent } from './juice/juice.component';
import { LoveComponent } from './love/love.component';
import { MeatComponent } from './meat/meat.component';
import { VegitablesComponent } from './vegitables/vegitables.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { TransactionComponent } from './transaction/transaction.component';
import { UpiPaymentComponent } from './upi-payment/upi-payment.component';
import { CardPaymentComponent } from './card-payment/card-payment.component';
import { NetbankingPaymentComponent } from './netbanking-payment/netbanking-payment.component';
import { PaymentsComponent } from './payments/payments.component';

const routes: Routes = [
  {path: '', redirectTo:'home',pathMatch:'full'},
  {path: 'home', component:HomeComponent},
  {path: 'category', component:CategoryComponent},
  {path: 'cart', component:CartComponent},
  {path: 'prodect', component:ProdectComponent},
  {path: 'deal', component:DealComponent},
  {path: 'contact', component:ContactComponent},
  {path: 'fruits', component:FruitsComponent},
  {path: 'juice', component:JuiceComponent},
  {path: 'love', component:LoveComponent},
  {path: 'meat', component:MeatComponent},
  {path: 'vegitables', component:VegitablesComponent},
  {path: 'login', component:LoginComponent},
  {path: 'signup', component:SignupComponent},
  { path:'transaction', component:TransactionComponent} ,
  { path:'upi', component:UpiPaymentComponent },
  { path:'card',component:CardPaymentComponent },
  { path:'netbanking', component:NetbankingPaymentComponent },
  { path:'payments', component:PaymentsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
