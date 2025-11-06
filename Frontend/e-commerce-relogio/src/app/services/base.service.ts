import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { PaginatedResponse } from '../models/paginated-response.model';

export class BaseService {
  constructor(protected http: HttpClient, protected baseUrl: string) {}

  protected getPaginated<T>(page: number, size: number, q?: string) : Observable<PaginatedResponse<T>> {
    let params = new HttpParams()
      .set('_page', page.toString())
      .set('_limit', size.toString());

    if (q) params = params.set('q', q);

    return this.http.get<T[]>(this.baseUrl, { params, observe: 'response' })
      .pipe(map(resp => {
        const total = Number(resp.headers.get('X-Total-Count') || resp.body?.length || 0);
        const content = resp.body || [];
        const totalPages = size ? Math.ceil(total / size) : 1;
        return {
          content,
          totalElements: total,
          totalPages,
          size,
          number: page
        } as PaginatedResponse<T>;
      }));
  }
}
