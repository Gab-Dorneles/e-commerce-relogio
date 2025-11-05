import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { Router } from "@angular/router"
import { AuthService } from "../../services/auth.service"
import type { User } from "../../services/auth.service"

@Component({
  selector: "app-admin-profile",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./admin-profile.component.html",
  styleUrls: ["./admin-profile.component.css"],
})
export class AdminProfileComponent implements OnInit {
  user: User | null = null

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getCurrentUser()

    // Redireciona se não estiver logado ou não for admin
    if (!this.user || this.user.role !== "admin") {
      this.router.navigate(["/"])
    }
  }

  logout(): void {
    this.authService.logout()
  }

  goToHome(): void {
    this.router.navigate(["/"])
  }
}
