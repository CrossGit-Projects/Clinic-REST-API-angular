import {HTTP_INTERCEPTORS, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';

import {JwtStorageService} from '../_services/jwt-storage.service';


const TOKEN_HEADER_KEY_JWT = 'AuthorizationJWT';


@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private token: JwtStorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let authReq = req;
    const token = this.token.getToken();
    if (token != null) {
      authReq = req.clone({
        headers: req.headers.set(TOKEN_HEADER_KEY_JWT, 'BearerJWT ' + token)
      });
    }
    return next.handle(authReq);
  }
}

export const authInterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
];
