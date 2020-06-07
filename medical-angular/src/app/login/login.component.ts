import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {JwtStorageService} from '../_services/jwt-storage.service';
import {HttpClient, HttpParams} from '@angular/common/http';
import {OAuth2StorageService} from '../_services/oauth2-storage.service';
import {Subscription} from 'rxjs';
import {Auth2Service} from '../_services/auth2.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  // tslint:disable-next-line:max-line-length
  constructor(private authService: AuthService, private auth2Service: Auth2Service, private tokenStorage: JwtStorageService, private tokenStorageOAuth2: OAuth2StorageService) {
  }
  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }
  onSubmit() {
    const body = new HttpParams()
      .set('username', this.form.username)
      .set('password', this.form.password)
      .set('grant_type', 'password')
      .set('scope', 'read');
    this.authService.login(this.form).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.auth2Service.getOauth2(body.toString()).subscribe(
          data1 => {
            console.log(data1);
            this.tokenStorageOAuth2.saveToken(JSON.stringify(data1));
            this.reloadPage();
          },
          err => {
            this.errorMessage = err.error.message;
          }
        );
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }
  reloadPage() {
    window.location.reload();
  }
}


