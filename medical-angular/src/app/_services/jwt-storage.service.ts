import { Injectable } from '@angular/core';

const TOKEN_JWT_KEY = 'jwt-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class JwtStorageService {

  constructor() { }

  signOut() {
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_JWT_KEY);
    window.sessionStorage.setItem(TOKEN_JWT_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_JWT_KEY);
  }

  public saveUser(user) {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser() {
    return JSON.parse(sessionStorage.getItem(USER_KEY));
  }
}
