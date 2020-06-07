import { NgModule } from '@angular/core';

// Importy związane z Angular Material, dzięki oddelegowaniu ich do osobnej klasy
// app.modue.ts jest bardziej czytelne

import {
  MatButtonModule,
  MatCheckboxModule,
  MatListModule,
  MatTableModule,
  MatDividerModule,
  MatSidenavModule,
  MatPaginatorModule,
  MatFormFieldModule,
  MatInputModule,
  MatExpansionModule,
  MatDialogModule,
  MatRadioModule,
  MatIconModule,
  MatToolbarModule,
  MatSnackBarModule,
  MatTabsModule
} from '@angular/material';


@NgModule({
  imports: [
    MatButtonModule,
    MatCheckboxModule,
    MatSidenavModule,
    MatDividerModule,
    MatListModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatExpansionModule,
    MatDialogModule,
    MatCheckboxModule,
    MatRadioModule,
    MatIconModule,
    MatToolbarModule,
    MatSnackBarModule,
    MatTabsModule
  ],
  exports: [
    MatButtonModule,
    MatCheckboxModule,
    MatSidenavModule,
    MatDividerModule,
    MatListModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatInputModule,
    MatExpansionModule,
    MatDialogModule,
    MatCheckboxModule,
    MatRadioModule,
    MatIconModule,
    MatToolbarModule,
    MatSnackBarModule,
    MatTabsModule
  ]
})
export class MaterialModule { }
