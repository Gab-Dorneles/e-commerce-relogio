import { Injectable } from "@angular/core"
import { Observable, of } from "rxjs"
import { delay } from "rxjs/operators"
import type { Relogio, PaginatedResponse } from "../models/relogio.model"

const STORAGE_KEY = "admin_relogios_v2"

@Injectable({ providedIn: "root" })
export class AdminRelogioService {
  private items: Relogio[] = []

  constructor() {
    const raw = localStorage.getItem(STORAGE_KEY)
    if (raw) {
      try {
        this.items = JSON.parse(raw) as Relogio[]
      } catch {
        this.items = []
      }
    } else {
      // semente inicial opcional
      this.items = []
      this.save()
    }
  }

  private save() {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(this.items))
  }

  // ✅ listagem com paginação
  list(page = 0, size = 10): Observable<PaginatedResponse<Relogio>> {
    const start = page * size
    const end = start + size
    const content = this.items.slice(start, end)
    const totalElements = this.items.length
    const totalPages = Math.ceil(totalElements / size)

    const response: PaginatedResponse<Relogio> = {
      content,
      totalElements,
      totalPages,
      size,
      number: page,
    }

    return of(response).pipe(delay(150))
  }

  get(id: number): Observable<Relogio | undefined> {
    return of(this.items.find((r) => r.id === id)).pipe(delay(100))
  }

  create(payload: Omit<Relogio, "id">): Observable<Relogio> {
    const id = this.items.length ? Math.max(...this.items.map((i) => i.id)) + 1 : 1
    const item: Relogio = { id, ...payload }
    this.items.unshift(item)
    this.save()
    return of(item).pipe(delay(150))
  }

  update(id: number, payload: Partial<Omit<Relogio, "id">>): Observable<Relogio | undefined> {
    const idx = this.items.findIndex((r) => r.id === id)
    if (idx === -1) return of(undefined).pipe(delay(100))
    this.items[idx] = { ...this.items[idx], ...payload }
    this.save()
    return of(this.items[idx]).pipe(delay(150))
  }

  delete(id: number): Observable<boolean> {
    const idx = this.items.findIndex((r) => r.id === id)
    if (idx === -1) return of(false).pipe(delay(100))
    this.items.splice(idx, 1)
    this.save()
    return of(true).pipe(delay(120))
  }
}
