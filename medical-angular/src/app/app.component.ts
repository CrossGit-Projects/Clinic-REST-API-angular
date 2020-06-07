import { Component, OnInit } from '@angular/core';
import { JwtStorageService } from './_services/jwt-storage.service';
import { OAuth2StorageService } from './_services/oauth2-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  private roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showDoctorBoard = false;
  username: string;

  constructor(private tokenStorageService: JwtStorageService, private tokenStorageServiceOAuth2: OAuth2StorageService) { }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showDoctorBoard = this.roles.includes('ROLE_DOCTOR');

      this.username = user.username;
    }
  }

  logout() {
    this.tokenStorageService.signOut();
    this.tokenStorageServiceOAuth2.signOut();
    window.location.reload();
  }
}
