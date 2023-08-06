import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Category } from 'src/app/models/category.model';
import { Product } from 'src/app/models/product.model';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent {
  categories: Category[] = [];

  constructor(private productService: ProductService,
    private categoryService: CategoryService) {}

  ngOnInit() {
    this.categoryService.getCategories().subscribe(data => 
      this.categories = data
    );
  }

  handleSubmit(addProductForm: NgForm) {
    console.log(addProductForm);
    console.log(addProductForm.value);
    
    console.log(addProductForm.value.name);
    console.log(addProductForm.value.category);
    console.log(addProductForm.value.active);

    const formValue = addProductForm.value;

    const newProduct = new Product(
      formValue.name,
      formValue.price,
      formValue.image,
      formValue.active,
      formValue.description,
      formValue.stock,
      new Category(formValue.category)
    );

    // .subscribe -> async
    this.productService.addProduct(newProduct).subscribe();

  }
}
