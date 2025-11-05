import { Component, Input } from "@angular/core"
import { CommonModule } from "@angular/common"
import type { Relogio } from "../../models/relogio.model"

@Component({
  selector: "app-relogio-card",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./relogio-card.component.html",
  styleUrls: ["./relogio-card.component.css"],
})
export class RelogioCardComponent {
  @Input() relogio!: Relogio

  formatPrice(price: number): string {
    return price.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })
  }
}
