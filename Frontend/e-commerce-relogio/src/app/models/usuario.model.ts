export interface Usuario {
  id?: number;
  name: string;
  email: string;
  password: string;
  role: 'cliente' | 'adm';
}
