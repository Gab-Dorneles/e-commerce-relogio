import { Component, ViewChild } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RelogioListComponent } from "./components/relogio-list/relogio-list.component"

@Component({
  selector: "app-root",
  standalone: true,
  imports: [CommonModule, RelogioListComponent],
  template: `
    <div class="min-h-screen bg-background">
      <header class="bg-card border-b border-border shadow-sm">
        <div class="container mx-auto px-4 py-6 flex flex-col md:flex-row md:items-center md:justify-between gap-4">
          <div>
            <h1 class="text-2xl italic font-bold text-foreground font-serif">Relógios Premium</h1>
            <p class="text-muted-foreground mt-2 italic text-sm">Elegância e precisão em cada detalhe</p>
          </div>
          <!-- Minimal navigation (elegant pills side-by-side) -->
          <nav aria-label="Main navigation">
            <ul class="flex gap-3 items-center">
              <li>
                <button
                  class="px-3 py-1 rounded-full text-sm transition-colors"
                  [ngClass]="{ 'bg-primary text-primary-foreground': activeFilter === '', 'text-foreground/80': activeFilter !== '' }"
                  (click)="applyFilter('')"
                  [attr.aria-current]="activeFilter === '' ? 'page' : null"
                >
                  Relógios
                </button>
              </li>
              <li>
                <button
                  class="px-3 py-1 rounded-full text-sm transition-colors"
                  [ngClass]="{ 'bg-primary text-primary-foreground': activeFilter === 'Masculino', 'text-foreground/80': activeFilter !== 'Masculino' }"
                  (click)="applyFilter('Masculino')"
                  [attr.aria-current]="activeFilter === 'Masculino' ? 'page' : null"
                >
                  Masculinos
                </button>
              </li>
              <li>
                <button
                  class="px-3 py-1 rounded-full text-sm transition-colors"
                  [ngClass]="{ 'bg-primary text-primary-foreground': activeFilter === 'Feminino', 'text-foreground/80': activeFilter !== 'Feminino' }"
                  (click)="applyFilter('Feminino')"
                  [attr.aria-current]="activeFilter === 'Feminino' ? 'page' : null"
                >
                  Femininos
                </button>
              </li>
            </ul>
          </nav>
        </div>
      </header>
      <main class="container mx-auto px-4 py-8">
        <app-relogio-list></app-relogio-list>
      </main>
      <footer class="bg-card border-t border-border mt-16">
        <div class="container mx-auto px-4 py-8 text-center text-muted-foreground">
          <p>&copy; 2025 Relógios Premium. Todos os direitos reservados.</p>
        </div>
      </footer>
    </div>
  `,
})
export class AppComponent {
  @ViewChild(RelogioListComponent) relogioList?: RelogioListComponent
  // currently selected filter (used to style active nav item)
  activeFilter = ''

  applyFilter(filter: string): void {
    // map common labels to dataset categories if needed
    const mapped = filter === 'Masculino' ? 'Masculino' : filter === 'Feminino' ? 'Feminino' : ''
    this.activeFilter = mapped
    // apply as genero filter on the list component
    this.relogioList?.setGender(mapped)
  }
}