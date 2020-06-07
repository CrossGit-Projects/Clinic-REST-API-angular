import {HTTP_INTERCEPTORS, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';

import {OAuth2StorageService} from '../_services/oauth2-storage.service';


const TOKEN_HEADER_KEY_OAUTH2 = 'Authorization';

@Injectable()
export class Oauth2Interceptor implements HttpInterceptor {
  constructor(private tokenOAuth2: OAuth2StorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let authReqOAuth = req;
    const tokenOAuth2 = this.tokenOAuth2.getToken();
    if (tokenOAuth2 != null) {
      authReqOAuth = req.clone({
        headers: req.headers.set(TOKEN_HEADER_KEY_OAUTH2, 'Bearer ' + tokenOAuth2)
      });
    }
    return next.handle(authReqOAuth);
  }
}

export const auth2InterceptorProviders = [
  {provide: HTTP_INTERCEPTORS, useClass: Oauth2Interceptor, multi: true}
];
