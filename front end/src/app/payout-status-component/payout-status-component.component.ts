import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-payout-status-component',
  templateUrl: './payout-status-component.component.html',
  styleUrl: './payout-status-component.component.css'
})
export class PayoutStatusComponentComponent implements OnInit {
  message: string ='';

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    const path = this.route.snapshot.url[0].path;
    this.message = path === 'payout-success'
      ? 'Your payout has been sent successfully!'
      : 'Unfortunately, your payout could not be processed. Please try again later.';
  }
}
