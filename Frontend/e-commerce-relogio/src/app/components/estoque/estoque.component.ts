import { Component, OnInit } from '@angular/core';
import { Estoque } from '../../models/estoque.model';
import { EstoqueService } from '../../services/estoque.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-estoque',
  templateUrl: './estoque.component.html',
  styleUrls: ['./estoque.component.css']
})
export class EstoqueComponent implements OnInit {
  estoques: Estoque[] = [];
  form: FormGroup;
  editing = false;
  editingId?: number;

  constructor(private estoqueService: EstoqueService, private fb: FormBuilder) {
    this.form = this.fb.group({
      relogioId: [null, Validators.required],
      quantidade: [0, [Validators.required, Validators.min(0)]],
      local: ['']
    });
  }

  ngOnInit(): void { this.load(); }

  load() { this.estoqueService.getAll().subscribe(e => this.estoques = e); }

  startCreate() { this.editing = true; this.editingId = undefined; this.form.reset({ relogioId:null, quantidade:0, local:'' }); }

  startEdit(e: Estoque) { this.editing = true; this.editingId = e.id; this.form.setValue({ relogioId: e.relogioId, quantidade: e.quantidade, local: e.local || '' }); }

  cancel() { this.editing = false; this.editingId = undefined; }

  save() {
    if (this.form.invalid) return;
    const payload: Estoque = { ...this.form.value } as Estoque;
    if (this.editingId) {
      payload.id = this.editingId;
      this.estoqueService.update(payload).subscribe(() => { this.load(); this.cancel(); });
    } else {
      this.estoqueService.create(payload).subscribe(() => { this.load(); this.cancel(); });
    }
  }

  remove(id?: number) { if (!id) return; if (!confirm('Confirma exclusão?')) return; this.estoqueService.delete(id).subscribe(() => this.load()); }
}
