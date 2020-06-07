import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH2_API = 'http://localhost:8081/api/oauth/token';


@Injectable({
  providedIn: 'root'
})
export class Auth2Service {

  constructor(private http: HttpClient) { }
  getOauth2(loginPayload): Observable<any> {
    const headers = {
      Authorization: 'Basic ' + btoa('R2dpxQ3vPrtfgF72:fDw7Mpkk5czHNuSRtmhGmAGL42CaxQB9'),
      'Content-type': 'application/x-www-form-urlencoded'
    };
    return this.http.post(AUTH2_API, loginPayload, {headers});
  }
}
