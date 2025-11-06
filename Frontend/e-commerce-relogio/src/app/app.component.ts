import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { RouterOutlet, RouterLink, Router } from "@angular/router"
import { AuthService } from "./services/auth.service"
import type { User } from "./services/auth.service"

@Component({
  selector: "app-root",
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent implements OnInit {
  title = "Chronos Relógios"
  currentUser: User | null = null

  // 🌗 Variável para armazenar o estado do tema
  isDarkMode = false

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe((user: User | null) => {
      this.currentUser = user
    })
  }

  logout(): void {
    this.authService.logout()
  }

  goToProfile(): void {
    if (this.currentUser?.role === "admin") {
      this.router.navigate(["/admin-profile"])
    } else {
      this.router.navigate(["/client-profile"])
    }
  }

  // 🌙 Alterna entre tema claro e escuro
  toggleTheme(): void {
    this.isDarkMode = !this.isDarkMode

    if (this.isDarkMode) {
      document.body.classList.add("dark-mode")
    } else {
      document.body.classList.remove("dark-mode")
    }
  }
}
