import { Injectable } from '@angular/core';

const TOKEN_OAUTH2_KEY = 'token-oauth';

@Injectable({
  providedIn: 'root'
})
export class OAuth2StorageService {

  constructor() { }

  signOut() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_OAUTH2_KEY);
    window.sessionStorage.setItem(TOKEN_OAUTH2_KEY, JSON.parse(token).access_token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_OAUTH2_KEY);
  }
}
