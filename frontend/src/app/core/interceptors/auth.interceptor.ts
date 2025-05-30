import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const raw = sessionStorage.getItem('user');
  if (!raw) return next(req);

  const user = JSON.parse(raw);
  if (!user.token) return next(req);

  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${user.token}`
    }
  });

  return next(authReq);
};
