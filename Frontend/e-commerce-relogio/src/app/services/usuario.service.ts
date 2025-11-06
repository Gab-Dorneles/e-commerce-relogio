import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../models/usuario.model';
import { Observable } from 'rxjs';
import { PaginatedResponse } from '../models/paginated-response.model';


@Injectable({ providedIn: 'root' })
export class UsuarioService extends BaseService {
  private base = 'http://localhost:3000/usuarios';
  constructor(protected http: HttpClient) { super(http, 'http://localhost:3000/usuarios'); }

  getAll(): Observable<Usuario[]> { return this.http.get<Usuario[]>(this.base); }
  getById(id: number): Observable<Usuario> { return this.http.get<Usuario>(`${this.base}/${id}`); }
  create(u: Usuario): Observable<Usuario> { return this.http.post<Usuario>(this.base, u); }
  update(u: Usuario): Observable<Usuario> { return this.http.put<Usuario>(`${this.base}/${u.id}`, u); }
  delete(id: number): Observable<any> { return this.http.delete(`${this.base}/${id}`); }

  findByEmail(email: string) { return this.http.get<Usuario[]>(`${this.base}?email=${email}`); }

  getPaginated(page: number, size: number, q?: string): Observable<PaginatedResponse<Usuario>> {
    return super.getPaginated<Usuario>(page, size, q);
  }
}
