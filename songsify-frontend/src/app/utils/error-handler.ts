import { HttpErrorResponse } from '@angular/common/http';

export function extractErrorMessage(err: HttpErrorResponse): string {
  const body = err.error;

  if (!body) {
    return 'Ocurrió un error inesperado';
  }

  if (body.error) {
    return body.error;
  }

  const mensajes = Object.values(body) as string[];
  if (mensajes.length > 0) {
    return mensajes.join(' — ');
  }

  return 'Ocurrió un error inesperado';
}