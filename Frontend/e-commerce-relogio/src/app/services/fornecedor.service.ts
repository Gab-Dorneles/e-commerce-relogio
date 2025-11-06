import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Fornecedor } from '../models/fornecedor.model';
import { Observable } from 'rxjs';
import { PaginatedResponse } from '../models/paginated-response.model';
import { BaseService } from './base.service';

@Injectable({ providedIn: 'root' })
export class FornecedorService extends BaseService {
  private base = 'http://localhost:3000/fornecedores';
  constructor(protected http: HttpClient) { super(http, 'http://localhost:3000/fornecedores'); }

  getAll(): Observable<Fornecedor[]> { return this.http.get<Fornecedor[]>(this.base); }
  getById(id: number): Observable<Fornecedor> { return this.http.get<Fornecedor>(`${this.base}/${id}`); }
  create(f: Fornecedor): Observable<Fornecedor> { return this.http.post<Fornecedor>(this.base, f); }
  update(f: Fornecedor): Observable<Fornecedor> { return this.http.put<Fornecedor>(`${this.base}/${f.id}`, f); }
  delete(id: number): Observable<any> { return this.http.delete(`${this.base}/${id}`); }

  getPaginated(page: number, size: number, q?: string): Observable<PaginatedResponse<Fornecedor>> {
    return super.getPaginated<Fornecedor>(page, size, q);
  }
}
