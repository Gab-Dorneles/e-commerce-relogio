import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Estoque } from '../models/estoque.model';
import { Observable } from 'rxjs';
import { PaginatedResponse } from '../models/paginated-response.model';
import { BaseService } from './base.service';

@Injectable({ providedIn: 'root' })
export class EstoqueService extends BaseService {
  private base = 'http://localhost:3000/estoques';
  constructor(protected http: HttpClient) { super(http, 'http://localhost:3000/estoques'); }

  getAll(): Observable<Estoque[]> { return this.http.get<Estoque[]>(this.base); }
  getById(id: number): Observable<Estoque> { return this.http.get<Estoque>(`${this.base}/${id}`); }
  create(e: Estoque): Observable<Estoque> { return this.http.post<Estoque>(this.base, e); }
  update(e: Estoque): Observable<Estoque> { return this.http.put<Estoque>(`${this.base}/${e.id}`, e); }
  delete(id: number): Observable<any> { return this.http.delete(`${this.base}/${id}`); }

  getPaginated(page: number, size: number, q?: string): Observable<PaginatedResponse<Estoque>> {
    return super.getPaginated<Estoque>(page, size, q);
  }
}
