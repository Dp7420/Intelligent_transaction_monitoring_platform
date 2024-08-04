import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
// import { Router } from '@angular/router';

@Component({
  selector: 'app-prodect',
  templateUrl: './prodect.component.html',
  styleUrls: ['./prodect.component.css']
})
export class ProdectComponent implements OnInit{
  products = [
    { id: 1, name: 'Handmade Elephant', price: 10.50, originalPrice: 13.20, image: 'assets/images/handmadeele.jpg', discount: 33, quantity: 1 },
    { id: 2, name: 'Kondapalli Toys', price: 10.50, originalPrice: 13.20, image: 'assets/images/kondapally.jpg', discount: 45, quantity: 1 },
    { id: 3, name: 'Bamboo Stool', price: 10.50, originalPrice: 13.20, image: 'assets/images/handmadecamboo.jpg', discount: 52, quantity: 1 },
    { id: 4, name: 'Wooden Jewelry Box', price: 10.50, originalPrice: 13.20, image: 'assets/images/wooden.jpg', discount: 13, quantity: 1 },
    { id: 5, name: 'Home Toys', price: 10.50, originalPrice: 13.20, image: 'assets/images/toys.jpg', discount: 20, quantity: 1 },
    { id: 6, name: 'Handmade Horse', price: 10.50, originalPrice: 13.20, image: 'assets/images/horse.jpg', discount: 29, quantity: 1 },
    { id: 7, name: 'Handmade Baskets', price: 10.50, originalPrice: 13.20, image: 'assets/images/bags.jpg', discount: 55, quantity: 1 },
    { id: 8, name: 'Traditional Bags', price: 10.50, originalPrice: 13.20, image: 'assets/images/tbags.jpg', discount: 30, quantity: 1 },
    { id: 9, name: 'Ganesh Statue', price: 10.50, originalPrice: 13.20, image: 'assets/images/ganesh.webp', discount: 40, quantity: 1 }
  ];

  constructor(private cartService: CartService) { }

  ngOnInit(): void { }

  addToCart(item: any): void {
    this.cartService.addToCart(item);
    console.log('Product added to cart:', item);
  }

  getBadgeColor(discount: number): string {
    return discount > 50 ? '#d2f8d2' : '#f8d2d2'; 
  }
}
