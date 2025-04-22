export interface DifficultyOption {
  level: 'Fácil' | 'Medio' | 'Difícil';
  goalMoney: number;
  timeLimit: number;
  maxCapacity: number;
  initialLife: number;
}
