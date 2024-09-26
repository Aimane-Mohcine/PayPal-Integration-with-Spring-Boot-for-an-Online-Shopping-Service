import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-cancel',
  templateUrl: './cancel.component.html',
  styleUrl: './cancel.component.css'
})
export class CancelComponent  implements OnInit {
  constructor(private router: Router) { }  // Correct: properties are declared in the constructor

  ngOnInit(): void {

  }

  navigateToHome(): void {
    this.router.navigate(['/aa']);
  }
}
