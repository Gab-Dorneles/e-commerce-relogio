import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { RelogioService } from "../../services/relogio.service"
import type { Relogio, PaginatedResponse } from "../../models/relogio.model"
import { RelogioCardComponent } from "../relogio-card/relogio-card.component"

@Component({
  selector: "app-relogio-list",
  standalone: true,
  imports: [CommonModule, FormsModule, RelogioCardComponent],
  templateUrl: "./relogio-list.component.html",
  styleUrls: ["./relogio-list.component.css"],
})
export class RelogioListComponent implements OnInit {
  relogios: Relogio[] = []
  searchTerm = ""
  selectedGenero = ""
  currentPage = 0
  pageSize = 6
  totalPages = 0
  totalElements = 0
  isLoading = false
  pages: number[] = []

  constructor(private relogioService: RelogioService) {}

  ngOnInit(): void {
    this.loadRelogios()
  }

  loadRelogios(): void {
    this.isLoading = true
  this.relogioService.findAll(this.currentPage, this.pageSize, this.searchTerm, this.selectedGenero).subscribe({
      next: (response: PaginatedResponse<Relogio>) => {
        this.relogios = response.content
        this.totalPages = response.totalPages
        this.totalElements = response.totalElements
        this.updatePages()
        this.isLoading = false
      },
      error: (error) => {
        console.error("Erro ao carregar relógios:", error)
        this.isLoading = false
      },
    })
  }

  onSearch(): void {
    this.currentPage = 0
    this.loadRelogios()
  }

  clearSearch(): void {
    this.searchTerm = ''
    this.selectedGenero = ''
    this.currentPage = 0
    this.loadRelogios()
  }

  // Allow parent to set genero filter explicitly
  setGender(genero: string): void {
    this.selectedGenero = genero
    this.currentPage = 0
    this.loadRelogios()
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page
      this.loadRelogios()
      window.scrollTo({ top: 0, behavior: "smooth" })
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.goToPage(this.currentPage - 1)
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.goToPage(this.currentPage + 1)
    }
  }

  updatePages(): void {
    this.pages = []
    const maxPagesToShow = 5
    let startPage = Math.max(0, this.currentPage - Math.floor(maxPagesToShow / 2))
    const endPage = Math.min(this.totalPages - 1, startPage + maxPagesToShow - 1)

    if (endPage - startPage < maxPagesToShow - 1) {
      startPage = Math.max(0, endPage - maxPagesToShow + 1)
    }

    for (let i = startPage; i <= endPage; i++) {
      this.pages.push(i)
    }
  }

  formatPrice(price: number): string {
    return price.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })
  }

  // Allow parent components to set a filter programmatically
  setFilter(filter: string): void {
    this.searchTerm = filter
    this.currentPage = 0
    this.loadRelogios()
  }
}
