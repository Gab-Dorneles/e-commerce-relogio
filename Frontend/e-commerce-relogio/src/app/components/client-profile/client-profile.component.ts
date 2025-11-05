import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { Router } from "@angular/router"
import { AuthService } from "../../services/auth.service"
import type { User } from "../../services/auth.service"

@Component({
  selector: "app-client-profile",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./client-profile.component.html",
  styleUrls: ["./client-profile.component.css"],
})
export class ClientProfileComponent implements OnInit {
  user: User | null = null

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser()

    // Redireciona se não estiver logado
    if (!this.user) {
      this.router.navigate(["/"])
    }
  }

  logout(): void {
    this.authService.logout()
  }

  goToHome(): void {
    this.router.navigate(["/"])
  }

  getInitial(): string {
    return this.user?.username ? this.user.username.charAt(0).toUpperCase() : ""
  }
}
