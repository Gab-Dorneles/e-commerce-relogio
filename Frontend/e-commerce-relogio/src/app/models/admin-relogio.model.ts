export interface RelogioAdmin {
  id: number
  nome: string
  marca: string
  preco: number
  descricao: string
  imagemUrl: string
  categoria: string
  genero?: string
  estoque: number
  destaque: boolean
}

export interface PaginatedResponse<T> {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
}
