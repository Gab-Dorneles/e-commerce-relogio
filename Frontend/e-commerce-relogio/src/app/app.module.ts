import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { RelogioComponent } from './components/relogio/relogio.component';
import { UsuarioComponent } from './components/usuario/usuario.component';
import { EstoqueComponent } from './components/estoque/estoque.component';
import { FornecedorComponent } from './components/fornecedor/fornecedor.component';
import { ClienteComponent } from './components/cliente/cliente.component';
import { LoginComponent } from './pages/login/login.component';

// services are providedIn: 'root' already
@NgModule({
  declarations: [
    AppComponent,
    RelogioComponent,
    UsuarioComponent,
    EstoqueComponent,
    FornecedorComponent,
    ClienteComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    LoginComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
