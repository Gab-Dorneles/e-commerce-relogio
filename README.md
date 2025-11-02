 E-commerce de Relógios - Chronos

Aplicação Angular para e-commerce de relógios premium.

## Funcionalidades

- Listagem de produtos em cards elegantes
- Sistema de busca por nome, marca e categoria
- Paginação completa dos resultados
- Design responsivo e moderno
- Indicadores de estoque
- Produtos em destaque
- Interface elegante inspirada em lojas de luxo

## Tecnologias

- Angular 19
- TypeScript
- RxJS
- Standalone Components
- CSS3 com variáveis customizadas

## Design

O design foi inspirado em e-commerces de luxo, com:
- Paleta de cores elegante (tons de cinza e preto)
- Tipografia sofisticada (Inter, Playfair Display, JetBrains Mono)
- Cards com hover effects suaves

## Como executar

\`\`\`bash
# Instalar dependências
npm install

# Executar em modo desenvolvimento
npm start

# Acessar em http://localhost:4200
\`\`\`

## Estrutura do Projeto

\`\`\`
src/
├── app/
│   ├── components/
│   │   ├── relogio-list/      # Componente principal com listagem
│   │   └── relogio-card/      # Card individual de produto
│   ├── models/
│   │   └── relogio.model.ts   # Interfaces e tipos
│   ├── services/
│   │   └── relogio.service.ts # Serviço de dados
│   └── app.component.ts       # Componente raiz
├── styles.css                 # Estilos globais
└── index.html                 # HTML principal
\`\`\`

## Funcionalidades de Busca

A busca funciona em tempo real e filtra por:
- Nome do relógio
- Marca
- Categoria
- Descrição

## Paginação

- 6 produtos por página (configurável)
- Navegação por números de página
- Botões anterior/próxima
- Indicador de total de resultados

