import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Caravan } from '@shared/models/caravan.model';
import { CaravanService } from '@core/services/caravan.service';
import { CurrentGameService } from '@core/services/current-game.service';


@Component({
  selector: 'app-select-caravan',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './select-caravan.component.html',
  styleUrls: ['./select-caravan.component.scss']
})
export class SelectCaravanComponent implements OnInit {

  private caravanService = inject(CaravanService);
  private router = inject(Router);
  private currentGame = inject(CurrentGameService);


  caravans = signal<Caravan[]>([]);
  currentIndex = 0;

  animateStats = false;
  animateImage = false;

  get selectedCaravan(): Caravan | null {
    return this.caravans()[this.currentIndex] ?? null;
  }

  ngOnInit(): void {
    this.caravanService.getAllCaravans().subscribe({
      next: (data) => {
        this.caravans.set(data);
      },
      error: err => console.error('Error loading caravans', err)
    });
  }


  selectCaravan(): void {
    const selected = this.selectedCaravan;
    if (selected) {
      this.currentGame.selectedGame.set({
        game: {
          idGame: 0,
          elapsedTime: 0,
          timeLimit: 0,
          minProfit: 0,
          caravan: selected,
          map: {} as any
        }
      });

      this.router.navigate(['/select-map']);
    }
  }


  prevCaravan(): void {
    const list = this.caravans();
    this.currentIndex = (this.currentIndex - 1 + list.length) % list.length;
    this.triggerAnimation();
  }

  nextCaravan(): void {
    const list = this.caravans();
    this.currentIndex = (this.currentIndex + 1) % list.length;
    this.triggerAnimation();
  }

  triggerAnimation(): void {
    this.animateStats = false;
    this.animateImage = false;
    setTimeout(() => {
      this.animateStats = true;
      this.animateImage = true;
    }, 0);
  }
}
