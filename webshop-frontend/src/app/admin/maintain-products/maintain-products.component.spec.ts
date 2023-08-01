import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaintainProductsComponent } from './maintain-products.component';

describe('MaintainProductsComponent', () => {
  let component: MaintainProductsComponent;
  let fixture: ComponentFixture<MaintainProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MaintainProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MaintainProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
