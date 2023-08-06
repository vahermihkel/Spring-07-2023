import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { CartComponent } from './cart/cart.component';
import { AddProductComponent } from './admin/add-product/add-product.component';
import { EditProductComponent } from './admin/edit-product/edit-product.component';
import { MaintainProductsComponent } from './admin/maintain-products/maintain-products.component';

const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'ostukorv', component: CartComponent },
  { path: 'lisa-toode', component: AddProductComponent },
  { path: 'muuda-toode/:id', component: EditProductComponent },
  { path: 'halda-tooteid', component: MaintainProductsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
