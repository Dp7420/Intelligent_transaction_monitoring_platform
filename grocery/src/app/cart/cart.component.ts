import { Component, OnInit } from '@angular/core';
import { Observable, Subscribable, filter, map } from 'rxjs';
import { CartService } from '../cart.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: any[] = [];

  constructor(private cartService: CartService, private router: Router) {}

  ngOnInit(): void {
    this.cartService.cart$.subscribe(items => this.cartItems = items);
  }

  getTotal() {
    return this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
  }

  proceedToPayment() {
    const totalAmount = this.getTotal();
    this.router.navigate(['/transaction'], { queryParams: { amount: totalAmount } });
  }

  increaseQuantity(index: number): void {
    this.cartItems[index].quantity++;
  }

  decreaseQuantity(index: number): void {
    if (this.cartItems[index].quantity > 1) {
      this.cartItems[index].quantity--;
    }
  }

  removeItem(index: number): void {
    this.cartItems.splice(index, 1);
  }
}