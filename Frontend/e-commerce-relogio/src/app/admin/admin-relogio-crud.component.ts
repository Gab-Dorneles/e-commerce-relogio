import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from "@angular/forms"
import { AdminRelogioService } from "../services/admin-relogio.service"
import type { Relogio, PaginatedResponse } from "../models/relogio.model"

@Component({
  selector: "app-admin-relogio-crud",
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: "./admin-relogio-crud.component.html",
  styleUrls: ["./admin-relogio-crud.component.css"],
})
export class AdminRelogioCrudComponent {
  relogios: Relogio[] = []
  editing: Relogio | null = null
  isLoading = false

  // paginação
  page = 0
  size = 10
  totalPages = 0
  totalElements = 0

  form!: FormGroup

  constructor(private service: AdminRelogioService, private fb: FormBuilder) {
    this.form = this.fb.group({
      nome: ["", Validators.required],
      marca: ["", Validators.required],
      preco: [0, [Validators.required, Validators.min(0)]],
      descricao: [""],
      imagemUrl: [""],
      categoria: [""],
      genero: [""],
      estoque: [0, [Validators.required, Validators.min(0)]],
      destaque: [false],
    })

    this.load()
  }

  load(page: number = this.page) {
    this.isLoading = true
    this.service.list(page, this.size).subscribe({
      next: (response: PaginatedResponse<Relogio>) => {
        this.relogios = response.content
        this.totalElements = response.totalElements
        this.totalPages = response.totalPages
        this.page = response.number
        this.isLoading = false
      },
      error: () => (this.isLoading = false),
    })
  }

  startCreate() {
    this.editing = null
    this.form.reset({
      nome: "",
      marca: "",
      preco: 0,
      descricao: "",
      imagemUrl: "",
      categoria: "",
      genero: "",
      estoque: 0,
      destaque: false,
    })
  }

  startEdit(item: Relogio) {
    this.editing = item
    this.form.patchValue({ ...item })
  }

  save() {
    if (this.form.invalid) return
    const value = this.form.value as Omit<Relogio, "id">
    this.isLoading = true

    const action$ = this.editing
      ? this.service.update(this.editing.id, value)
      : this.service.create(value)

    action$.subscribe({
      next: () => this.load(),
      complete: () => (this.isLoading = false),
    })
  }

  remove(id: number) {
    if (!confirm("Remover esse relógio?")) return
    this.service.delete(id).subscribe(() => this.load())
  }

  nextPage() {
    if (this.page < this.totalPages - 1) this.load(this.page + 1)
  }

  prevPage() {
    if (this.page > 0) this.load(this.page - 1)
  }
}
