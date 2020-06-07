import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { BoardPatientComponent } from './board-patient/board-patient.component';
import { BoardDoctorComponent } from './board-doctor/board-doctor.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';

// Delkaracja na jakim url ma się wyrenderować jaki komponent. Pojawi się w miejscu deklaracji <router-outlet>
const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'patient', component: BoardPatientComponent },
  { path: 'doctor', component: BoardDoctorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
