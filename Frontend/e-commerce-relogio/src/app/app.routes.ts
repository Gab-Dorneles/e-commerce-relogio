import type { Routes } from "@angular/router"
import { RelogioListComponent } from "./components/relogio-list/relogio-list.component"

export const routes: Routes = [
  { path: "", component: RelogioListComponent },
  {
    path: "login",
    loadComponent: () => import("./components/login/login.component").then((m) => m.LoginComponent),
  },
  {
    path: "admin-profile",
    loadComponent: () =>
      import("./components/admin-profile/admin-profile.component").then((m) => m.AdminProfileComponent),
  },
  {
    path: "client-profile",
    loadComponent: () =>
      import("./components/client-profile/client-profile.component").then((m) => m.ClientProfileComponent),
  },
  { path: "**", redirectTo: "" },
]
