import { test, expect } from '@playwright/test';

test('flujo completo - crear cuenta', async ({ page }) => {
  await page.goto('http://localhost:4200');

  // 🔹 Click en botón "Crear cuenta"
  await page.click('button:has-text("Crear cuenta")');

  // 🔹 Completar formulario de registro
  await page.fill('input[name="username"]', 'jugador_test');
  await page.fill('input[name="password"]', 'clave123');

  // 🔹 Click en "Registrar"
  await page.click('button:has-text("Registrar")');

  // ✅ Verifica redirección a login o éxito
  await expect(page).toHaveURL(/login/); // O cambiar según tu app
});
