import { Component } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { Router } from "@angular/router"
import { AuthService } from "../../services/auth.service"

@Component({
  selector: "app-login",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent {
  username = ""
  password = ""
  selectedRole: "admin" | "client" = "client"
  errorMessage = ""

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  onSubmit(): void {
    if (!this.username || !this.password) {
      this.errorMessage = "Por favor, preencha todos os campos"
      return
    }

    const success = this.authService.login(this.username, this.password, this.selectedRole)

    if (!success) {
      this.errorMessage = "Credenciais inválidas. Use a senha: 123456"
    }
  }

  goBack(): void {
    this.router.navigate(["/"])
  }
}
