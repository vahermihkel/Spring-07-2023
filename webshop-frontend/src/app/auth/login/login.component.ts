import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  constructor(private authService: AuthService,
    private router: Router) {}

  handleSubmit(loginForm: NgForm) {
    const formValue = loginForm.value;

    const loginFormData = {
      personalCode: formValue.email,
      password: formValue.password,
    };

    this.authService.login(loginFormData).subscribe(data => {
      sessionStorage.setItem("token",data.token);
      sessionStorage.setItem("expiration",data.expiration.toString());
      this.router.navigateByUrl("/halda-tooteid");
    });
  }
}
