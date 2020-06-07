import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-patient.component.html',
  styleUrls: ['./board-patient.component.css']
})
export class BoardPatientComponent implements OnInit {
  content = '';

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getPatientBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }
}
