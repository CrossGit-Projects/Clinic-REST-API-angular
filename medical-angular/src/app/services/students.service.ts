// import { Injectable } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { AppUser } from '../models/appUser';
// import { environment } from '../../environments/environment';
//
// // Zastosowanie serwisów jest ogromne. W naszym przypadku
// // zawiera logikę związaną z interakcją z zewnętrznym API.
//
// @Injectable({
//   providedIn: 'root'
// })
// export class StudentsService {
//   // Pobieranie z environment zmiennej środowiskowej reprezentującej url do serwisu studentów za pośrednictwem gateway
//   API_URL_USERS = environment.API_URL_USERS;
//
//   // W konstruktorze wstrzykiwany jest klient http
//   constructor(private httpClient: HttpClient) { }
//
//
//   // Metoda pobierająca liste studentów z studentService za pomocą endpointa /api/students (GET)
//   // Observable<Student[]> - To strumień który nasłuchuje na tablice studentów
//   // Za pomocą httpClient odpytywany jest StudentService
//   getStudents(): Observable<AppUser[]> {
//     return this.httpClient.get<AppUser[]>(this.API_URL_USERS);
//   }
//
//   // Metoda wywołująca endpoint /api/students (POST)
//   // Dodanie studenta w StudentService za pośrednictwem wywołania metody w Rest Api
//   saveStudent(appuser: AppUser): Observable<AppUser> {
//     return this.httpClient.post(this.API_URL_USERS, appuser);
//   }
// }
