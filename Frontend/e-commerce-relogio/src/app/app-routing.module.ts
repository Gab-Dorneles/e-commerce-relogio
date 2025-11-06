import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RelogioComponent } from './components/relogio/relogio.component';
import { UsuarioComponent } from './components/usuario/usuario.component';
import { EstoqueComponent } from './components/estoque/estoque.component';
import { FornecedorComponent } from './components/fornecedor/fornecedor.component';
import { ClienteComponent } from './components/cliente/cliente.component';
import { LoginComponent } from './pages/login/login.component';

const routes: Routes = [
  { path: '', redirectTo: 'relogios', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'relogios', component: RelogioComponent },
  { path: 'usuarios', component: UsuarioComponent },
  { path: 'estoques', component: EstoqueComponent },
  { path: 'fornecedores', component: FornecedorComponent },
  { path: 'clientes', component: ClienteComponent },
  { path: 'admin/relogios', loadComponent: () => import('./admin/admin-relogio-crud.component').then(m => m.AdminRelogioCrudComponent) },
  { path: '**', redirectTo: 'relogios' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
