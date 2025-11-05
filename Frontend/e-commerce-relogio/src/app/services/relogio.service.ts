import { Injectable } from "@angular/core"
import { type Observable, of } from "rxjs"
import { delay } from "rxjs/operators"
import type { Relogio, PaginatedResponse } from "../models/relogio.model"

@Injectable({
  providedIn: "root",
})
export class RelogioService {
  private relogios: Relogio[] = [
    {
      id: 1,
      nome: "Perpétuo Elite",
      marca: "Luxor",
      preco: 10000.0,
      descricao: "Relógio de luxo com movimento automático",
      imagemUrl: "/eliteluxor.png",
      categoria: "Luxo",
      genero: "Unissex",
      estoque: 5,
      destaque: true,
    },
    {
      id: 2,
      nome: "Sport Master",
      marca: "Luxor",
      preco: 1800.0,
      descricao: "Relógio esportivo resistente à água até 200m",
      imagemUrl: "/esportivomaster.png",
      categoria: "Esportivo",
      genero: "Unissex",
      estoque: 12,
      destaque: true,
    },
    {
      id: 3,
      nome: "Gentleman",
      marca: "Masculino",
      preco: 8900.0,
      descricao: "Design clássico atemporal com pulseira de couro",
      imagemUrl: "/classicelegance.png",
      categoria: "Couro",
      genero: "Masculino",
      estoque: 8,
      destaque: false,
    },
    {
      id: 4,
      nome: "Nexus",
      marca: "TechTime",
      preco: 1500.0,
      descricao: "Relógio digital com múltiplas funções",
      imagemUrl: "/techtime.png",
      categoria: "Digital",
      genero: "Unissex",
      estoque: 20,
      destaque: false,
    },
    {
      id: 5,
      nome: "Gold Edition",
      marca: "Prestige",
      preco: 15000.0,
      descricao: "Relógio banhado a ouro 18k com diamantes",
      imagemUrl: "/gold18.png",
      categoria: "Feminino",
      genero: "Feminino",
      estoque: 3,
      destaque: true,
    },
    {
      id: 6,
      nome: "Astrid Minimalist",
      marca: "Nordic",
      preco: 4000.0,
      descricao: "Design minimalista escandinavo",
      imagemUrl: "/nordic.png",
      categoria: "Minimalista",
      genero: "Unissex",
      estoque: 15,
      destaque: false,
    },
    {
      id: 7,
      nome: "Piloto",
      marca: "SkyMaster",
      preco: 6700.0,
      descricao: "Relógio de aviação com cronógrafo",
      imagemUrl: "/piloto.png",
      categoria: "Aviação",
      genero: "Masculino",
      estoque: 7,
      destaque: true,
    },
    {
      id: 8,
      nome: "Rose Princess",
      marca: "Feminine",
      preco: 4200.0,
      descricao: "Elegância feminina em ouro rosé",
      imagemUrl: "/princess.png",
      categoria: "Feminino",
      genero: "Feminino",
      estoque: 10,
      destaque: false,
    },
    {
      id: 9,
      nome: "Olavo Skeleton",
      marca: "Mechanical",
      preco: 15800.0,
      descricao: "Movimento visível com design skeleton",
      imagemUrl: "/automatico.png",
      categoria: "Masculino",
      genero: "Masculino",
      estoque: 4,
      destaque: true,
    },
    {
      id: 10,
      nome: "TecnoBlue",
      marca: "ConnectTime",
      preco: 2800.0,
      descricao: "Relógio híbrido com notificações smart",
      imagemUrl: "/hibrido.png",
      categoria: "Smart",
      genero: "Unissex",
      estoque: 18,
      destaque: false,
    },
    {
      id: 11,
      nome: "Vintage Bronze",
      marca: "Heritage",
      preco: 5400.0,
      descricao: "Estilo vintage com caixa em bronze",
      imagemUrl: "/vintage.png",
      categoria: "Vintage",
      genero: "Unissex",
      estoque: 6,
      destaque: false,
    },
    {
      id: 12,
      nome: "Pacífico Sul",
      marca: "OceanMaster",
      preco: 7200.0,
      descricao: "Relógio de mergulho profissional 500m",
      imagemUrl: "/oceano.png",
      categoria: "Esportivo",
      genero: "Masculino",
      estoque: 9,
      destaque: true,
    },
  ]

  constructor() {}

  findAll(page = 0, size = 6, searchTerm = "", genero = ""): Observable<PaginatedResponse<Relogio>> {
    let filteredRelogios = this.relogios

    // Filtrar por termo de busca
    if (searchTerm) {
      const term = searchTerm.toLowerCase()
      filteredRelogios = filteredRelogios.filter(
        (r) =>
          r.nome.toLowerCase().includes(term) ||
          r.marca.toLowerCase().includes(term) ||
          r.categoria.toLowerCase().includes(term) ||
          r.descricao.toLowerCase().includes(term),
      )
    }

    // Filtrar por gênero, se fornecido
    if (genero) {
      const g = genero.toLowerCase()
      filteredRelogios = filteredRelogios.filter((r) => (r.genero ?? "").toLowerCase() === g)
    }

    const totalElements = filteredRelogios.length
    const totalPages = Math.ceil(totalElements / size)
    const start = page * size
    const end = start + size
    const content = filteredRelogios.slice(start, end)

    const response: PaginatedResponse<Relogio> = {
      content,
      totalElements,
      totalPages,
      size,
      number: page,
    }

   
    return of(response).pipe(delay(300))
  }

  findById(id: number): Observable<Relogio | undefined> {
    const relogio = this.relogios.find((r) => r.id === id)
    return of(relogio).pipe(delay(200))
  }
}
