import { Component, type OnInit, OnDestroy } from "@angular/core"
import { CommonModule } from "@angular/common"
import { Router } from "@angular/router"
import { AuthService } from "../../services/auth.service"
import type { User } from "../../services/auth.service"
import { Subject } from "rxjs"
import { takeUntil } from "rxjs/operators"

@Component({
  selector: "app-client-profile",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./client-profile.component.html",
  styleUrls: ["./client-profile.component.css"],
})
export class ClientProfileComponent implements OnInit, OnDestroy {
  user: User | null = null
  private destroy$ = new Subject<void>()

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    // Assina currentUser$ para refletir mudanças em tempo real
    this.authService.currentUser$.pipe(takeUntil(this.destroy$)).subscribe((u) => {
      this.user = u
      // Redireciona se não estiver logado
      if (!this.user) {
        this.router.navigate(["/"])
      }
    })
  }

  ngOnDestroy(): void {
    this.destroy$.next()
    this.destroy$.complete()
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
