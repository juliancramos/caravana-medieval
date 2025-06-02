import {computed, effect, inject, Injectable, signal} from '@angular/core';
import { GameByPlayer } from '@shared/models/game-by-player.model';
import { HttpClient } from '@angular/common/http';
import { ActiveServiceDTO } from '@shared/models/active-service.dto';
import {ProductsByCaravan} from '@shared/models/products-by-caravan';
import { ProductsByCaravanService } from '@core/services/products-by-caravan.service';
import {Game} from '@shared/models/game.model';
import { ActiveServiceEffectDTO } from '@shared/models/ActiveServiceEffectDTO';


@Injectable({
  providedIn: 'root'
})
export class CurrentGameService {
  // estado general de la partida
  selectedGame = signal<GameByPlayer | null>(null);
  activeServices = signal<ActiveServiceDTO[]>([]);

  //para el header
  availableMoney = computed(() => this.selectedGame()?.game.caravan.availableMoney ?? 0);
  caravanName = computed(() => this.selectedGame()?.game?.caravan?.name);
  lifePoints = computed(() => this.selectedGame()?.game.caravan.lifePoints);
  minProfit = computed(() => this.selectedGame()?.game.minProfit);
  cityName = computed(() => this.selectedGame()?.game.caravan.currentCity.name);


  productsByCaravan = signal<ProductsByCaravan[]>([]);
  private productsService = inject(ProductsByCaravanService);

  remainingTime = computed(() => {
    const game = this.selectedGame()?.game;
    if (!game) return 0;

    return game.timeLimit - game.elapsedTime;
  });

  activeEffects = signal<ActiveServiceEffectDTO[]>([]);

  // Efecto para cargar productos y servicios  al seleccionar partida
  constructor(private http: HttpClient) {
    effect(() => {
      const caravanId = this.selectedGame()?.game.caravan.idCaravan;
      if (caravanId) {
        this.http.get<ProductsByCaravan[]>(`/api/products-by-caravan/products/caravan/${caravanId}`)
          .subscribe(res => {
            this.productsByCaravan.set(res);
          });
        this.http.get<ActiveServiceDTO[]>(`/api/services-by-caravan/active/caravan/${caravanId}`)
          .subscribe(services => {
            const filtered = services.filter(s => s.name.toLowerCase() !== 'reparar');
            this.activeServices.set(filtered);
          });

        this.http.get<ActiveServiceEffectDTO[]>(`/api/services-by-caravan/effects/caravan/${caravanId}`)
          .subscribe(effects => this.activeEffects.set(effects));
      }
    });
  }


  //Actualizar señales
  updateAvailableGold(productPrice: number): void {
    const current = this.selectedGame();
    if (!current) return;

    const updated = { ...current };
    updated.game.caravan.availableMoney += productPrice;

    this.selectedGame.set(updated);
  }

  updateLifePoints(delta: number): void {
    const current = this.selectedGame();
    if (!current) return;

    const updated = { ...current };
    updated.game.caravan.lifePoints += delta;
    this.selectedGame.set(updated);
  }

  updateElapsedTime(delta: number): void {
    const current = this.selectedGame();
    if (!current) return;

    const updated = { ...current };
    updated.game.elapsedTime += delta;
    this.selectedGame.set(updated);
  }

  updateCurrentCity(cityId: number, cityName: string): void {
    const current = this.selectedGame();
    if (!current) return;

    const updated = { ...current };
    updated.game.caravan.currentCity.idCity = cityId;
    updated.game.caravan.currentCity.name = cityName;
    this.selectedGame.set(updated);
  }

  caravanCurrentLoad = computed(() =>
    this.productsByCaravan().reduce((sum, item) => sum + item.product.weight * item.quantity, 0)
  );


  refreshGame(gameId: number): void {
    this.http.get<Game>(`/api/game/${gameId}`).subscribe({
      next: (updatedGame) => {
        const current = this.selectedGame();
        if (!current) return;
        this.selectedGame.set({ ...current, game: updatedGame });
      },
      error: err => {
        console.error('Error al refrescar la partida:', err);
      }
    });
  }

  hasWon = computed(() => {
    const game = this.selectedGame()?.game;
    if (!game) return false;

    return game.caravan.availableMoney >= game.minProfit;
  });


  private pollingIntervalId: any = null;

  private previousGold = this.availableMoney();
  public lastManualPurchaseTime = 0;
  public lastManualSaleTime = 0;

  //para actualizar el estado del juego cada 3 segundos
  startGameSyncPolling(): void {
    if (this.pollingIntervalId !== null) return; 

    this.pollingIntervalId = setInterval(() => {
      const gameId = this.selectedGame()?.game.idGame;
      const caravanId = this.selectedGame()?.game.caravan.idCaravan;

      if (!gameId || !caravanId) return;

      // Actualiza el objeto Game
      this.http.get<Game>(`/api/game/${gameId}`).subscribe({
        next: (updatedGame) => {
          const current = this.selectedGame();
          if (!current) return;

          const now = Date.now();
          const newGold = updatedGame.caravan.availableMoney;

          // Detectar compra hecha por otro jugador
          if (
            newGold < this.previousGold &&
            now - this.lastManualPurchaseTime > 2000
          ) {
            this.triggerGlobalFeedback(
              '¡Compra detectada!',
              'Otro jugador ha comprado un producto.'
            );
          }

          // Detectar venta hecha por otro jugador
          if (
            newGold > this.previousGold &&
            now - this.lastManualSaleTime > 2000
          ) {
            this.triggerGlobalFeedback(
              '¡Venta detectada!',
              'Otro jugador ha vendido un producto.'
            );
          }

          this.previousGold = newGold;

          // Actualiza la señal reactiva
          this.selectedGame.set({ ...current, game: updatedGame });
        },
        error: err => console.error('Error actualizando Game:', err)
      });

      // Actualiza los productos de la caravana (para peso, etc.)
      this.productsService.getProductsByCaravan(caravanId).subscribe({
        next: (res) => this.productsByCaravan.set(res),
        error: (err) => console.error('Error actualizando productos de caravana:', err)
      });
    }, 3000);
  }



  // Señales para mostrar popup global
  globalFeedbackVisible = signal(false);
  globalFeedbackTitle = signal('');
  globalFeedbackMessage = signal('');

  // Lanza un popup global desde cualquier evento 
  triggerGlobalFeedback(title: string, message: string, durationMs: number = 2000): void {
    console.log('  popup global:', title, message);
    this.globalFeedbackTitle.set(title);
    this.globalFeedbackMessage.set(message);
    this.globalFeedbackVisible.set(true);
    setTimeout(() => this.globalFeedbackVisible.set(false), durationMs);
  }





}
