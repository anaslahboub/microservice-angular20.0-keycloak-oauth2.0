import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule,
  ],
  templateUrl: './product.html',
  styleUrl: './product.css'
})
export class Product implements OnInit {

  products: any[] = [];

  constructor(private http: HttpClient) {
    // You can use HttpClient to make HTTP requests if needed

   }


  ngOnInit(): void {
    // Initialization logic can go here

    this.http.get('http://localhost:8091/products')
    .subscribe( {
      next: data => {
        this.products = data as any[];
        console.log('Products fetched successfully:', this.products);
      },
      error: error => {
        console.error('Error fetching products:', error);
      }
    });
  }

}
