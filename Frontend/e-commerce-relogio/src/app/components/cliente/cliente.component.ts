import { Component, OnInit } from '@angular/core';
import { Cliente } from '../../models/cliente.model';
import { ClienteService } from '../../services/cliente.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent implements OnInit {
  clientes: Cliente[] = [];
  form: FormGroup;
  editing = false;
  editingId?: number;

  constructor(private clienteService: ClienteService, private fb: FormBuilder) {
    this.form = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefone: [''],
      endereco: ['']
    });
  }

  ngOnInit(): void { this.load(); }

  load() { this.clienteService.getAll().subscribe(c => this.clientes = c); }

  startCreate() { this.editing = true; this.editingId = undefined; this.form.reset({ nome:'', email:'', telefone:'', endereco:'' }); }

  startEdit(c: Cliente) { this.editing = true; this.editingId = c.id; this.form.setValue({ nome: c.nome, email: c.email, telefone: c.telefone || '', endereco: c.endereco || '' }); }

  cancel() { this.editing = false; this.editingId = undefined; }

  save() {
    if (this.form.invalid) return;
    const payload: Cliente = { ...this.form.value } as Cliente;
    if (this.editingId) {
      payload.id = this.editingId;
      this.clienteService.update(payload).subscribe(() => { this.load(); this.cancel(); });
    } else {
      this.clienteService.create(payload).subscribe(() => { this.load(); this.cancel(); });
    }
  }

  remove(id?: number) { if (!id) return; if (!confirm('Confirma exclusão?')) return; this.clienteService.delete(id).subscribe(() => this.load()); }
}
