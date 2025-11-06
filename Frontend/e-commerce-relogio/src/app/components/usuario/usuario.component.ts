import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../models/usuario.model';
import { UsuarioService } from '../../services/usuario.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
  usuarios: Usuario[] = [];
  form: FormGroup;
  editing = false;
  editingId?: number;

  constructor(private usuarioService: UsuarioService, private fb: FormBuilder) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      role: ['cliente', Validators.required]
    });
  }

  ngOnInit(): void { this.load(); }

  load() { this.usuarioService.getAll().subscribe(u => this.usuarios = u); }

  startCreate() { this.editing = true; this.editingId = undefined; this.form.reset({ name:'', email:'', password:'', role:'cliente' }); }

  startEdit(u: Usuario) { this.editing = true; this.editingId = u.id; this.form.setValue({ name:u.name, email:u.email, password:u.password, role:u.role }); }

  cancel() { this.editing = false; this.editingId = undefined; }

  save() {
    if (this.form.invalid) return;
    const payload: Usuario = { ...this.form.value } as Usuario;
    if (this.editingId) {
      payload.id = this.editingId;
      this.usuarioService.update(payload).subscribe(() => { this.load(); this.cancel(); });
    } else {
      this.usuarioService.create(payload).subscribe(() => { this.load(); this.cancel(); });
    }
  }

  remove(id?: number) { if (!id) return; if (!confirm('Confirma exclusão?')) return; this.usuarioService.delete(id).subscribe(() => this.load()); }
}
