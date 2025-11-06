import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Cliente } from '../models/cliente.model';
import { Observable } from 'rxjs';
import { PaginatedResponse } from '../models/paginated-response.model';
import { BaseService } from './base.service';

@Injectable({ providedIn: 'root' })
export class ClienteService extends BaseService {
  private base = 'http://localhost:3000/clientes';
  constructor(protected http: HttpClient) { super(http, 'http://localhost:3000/clientes'); }

  getAll(): Observable<Cliente[]> { return this.http.get<Cliente[]>(this.base); }
  getById(id: number): Observable<Cliente> { return this.http.get<Cliente>(`${this.base}/${id}`); }
  create(c: Cliente): Observable<Cliente> { return this.http.post<Cliente>(this.base, c); }
  update(c: Cliente): Observable<Cliente> { return this.http.put<Cliente>(`${this.base}/${c.id}`, c); }
  delete(id: number): Observable<any> { return this.http.delete(`${this.base}/${id}`); }

  getPaginated(page: number, size: number, q?: string): Observable<PaginatedResponse<Cliente>> {
    return super.getPaginated<Cliente>(page, size, q);
  }
}
