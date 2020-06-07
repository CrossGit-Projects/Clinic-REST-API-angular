import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-moderator',
  templateUrl: './board-doctor.component.html',
  styleUrls: ['./board-doctor.component.css']
})
export class BoardDoctorComponent implements OnInit {
  content = '';

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.getDoctorBoard().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }
}
