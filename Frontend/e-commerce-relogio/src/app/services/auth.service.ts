import { Injectable } from "@angular/core"
import { Router } from "@angular/router"
import { BehaviorSubject, type Observable } from "rxjs"

export interface User {
  username: string
  role: "admin" | "client"
}

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<User | null>(null)
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable()

  constructor(private router: Router) {
    // Verifica se há usuário salvo no localStorage
    const savedUser = localStorage.getItem("currentUser")
    if (savedUser) {
      this.currentUserSubject.next(JSON.parse(savedUser))
    }
  }

  login(username: string, password: string, role: "admin" | "client"): boolean {
    // Simulação de autenticação (em produção, isso seria uma chamada API)
    if (password === "123456") {
      const user: User = { username, role }
      this.currentUserSubject.next(user)
      localStorage.setItem("currentUser", JSON.stringify(user))

      // Redireciona baseado no tipo de usuário
      if (role === "admin") {
        this.router.navigate(["/admin-profile"])
      } else {
        this.router.navigate(["/client-profile"])
      }
      return true
    }
    return false
  }

  logout(): void {
    this.currentUserSubject.next(null)
    localStorage.removeItem("currentUser")
    this.router.navigate(["/"])
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.value !== null
  }

  isAdmin(): boolean {
    return this.currentUserSubject.value?.role === "admin"
  }
}
