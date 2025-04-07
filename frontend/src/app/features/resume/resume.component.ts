import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.scss']
})
export class ResumeComponent {
  constructor(private router: Router) {}

  goToProducts(): void {
    //this.router.navigate(['/productos']);
  }

  goToServices(): void {
    //this.router.navigate(['/servicios']);
  }
}
