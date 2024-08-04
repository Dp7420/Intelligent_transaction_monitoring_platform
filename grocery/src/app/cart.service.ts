import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems: any[] = [];
  private cartSubject = new BehaviorSubject<any[]>([]);

  cart$ = this.cartSubject.asObservable();

  constructor() {
    // Optionally load cart items from localStorage
    const savedCartItems = localStorage.getItem('cartItems');
    if (savedCartItems) {
      this.cartItems = JSON.parse(savedCartItems);
      this.cartSubject.next(this.cartItems);
    }
  }

  addToCart(item: any) {
    const existingItem = this.cartItems.find(p => p.id === item.id);
    if (existingItem) {
      existingItem.quantity += item.quantity;
    } else {
      this.cartItems.push({ ...item });
    }
    this.cartSubject.next(this.cartItems);
    this.saveCartToLocalStorage();
  }

  getCartItems() {
    return this.cartItems;
  }

  private saveCartToLocalStorage() {
    localStorage.setItem('cartItems', JSON.stringify(this.cartItems));
  }

  clearCart(): void {
    localStorage.removeItem('cartItems');  // Adjust this based on your cart management
    // If you use a different method for managing the cart, implement it here.
  }
}
