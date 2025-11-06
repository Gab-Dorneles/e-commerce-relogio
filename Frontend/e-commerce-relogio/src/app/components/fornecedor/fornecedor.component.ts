import { Component, OnInit } from '@angular/core';
import { Fornecedor } from '../../models/fornecedor.model';
import { FornecedorService } from '../../services/fornecedor.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-fornecedor',
  templateUrl: './fornecedor.component.html',
  styleUrls: ['./fornecedor.component.css']
})
export class FornecedorComponent implements OnInit {
  fornecedores: Fornecedor[] = [];
  form: FormGroup;
  editing = false;
  editingId?: number;

  constructor(private fornecedorService: FornecedorService, private fb: FormBuilder) {
    this.form = this.fb.group({
      nome: ['', Validators.required],
      contato: [''],
      email: ['', Validators.email]
    });
  }

  ngOnInit(): void { this.load(); }

  load() { this.fornecedorService.getAll().subscribe(f => this.fornecedores = f); }

  startCreate() { this.editing = true; this.editingId = undefined; this.form.reset({ nome:'', contato:'', email:'' }); }

  startEdit(f: Fornecedor) { this.editing = true; this.editingId = f.id; this.form.setValue({ nome: f.nome, contato: f.contato || '', email: f.email || '' }); }

  cancel() { this.editing = false; this.editingId = undefined; }

  save() {
    if (this.form.invalid) return;
    const payload: Fornecedor = { ...this.form.value } as Fornecedor;
    if (this.editingId) {
      payload.id = this.editingId;
      this.fornecedorService.update(payload).subscribe(() => { this.load(); this.cancel(); });
    } else {
      this.fornecedorService.create(payload).subscribe(() => { this.load(); this.cancel(); });
    }
  }

  remove(id?: number) { if (!id) return; if (!confirm('Confirma exclusão?')) return; this.fornecedorService.delete(id).subscribe(() => this.load()); }
}
