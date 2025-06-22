INSERT INTO estado (nome, sigla) VALUES ('Acre', 'AC');
INSERT INTO estado (nome, sigla) VALUES ('Amazonas', 'AM');
INSERT INTO estado (nome, sigla) VALUES ('Goiás', 'GO');
INSERT INTO estado (nome, sigla) VALUES ('Pará', 'PA');
INSERT INTO estado (nome, sigla) VALUES ('Tocantins', 'TO');

INSERT INTO cidade (nome, id_estado) VALUES ('Manaus', 2);
INSERT INTO cidade (nome, id_estado) VALUES ('Palmas', 5);
INSERT INTO cidade (nome, id_estado) VALUES ('Guaraí', 5);
INSERT INTO cidade (nome, id_estado) VALUES ('Belém', 4);
INSERT INTO cidade (nome, id_estado) VALUES ('Goiânia', 3);

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Representando sofisticação intemporal, um design clássico com detalhes refinados.', 'ÉTERNEL');

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Focado em alta precisão e excelência em relojoaria.', 'PRÉCISION');

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Relógios que refletem um brilho elegante com materiais preciosos.', 'LUMIÈRE');

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Inspirado no cosmos, com designs únicos e detalhes celestiais.', 'ASTRAL');

INSERT INTO tiporelogio (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('tiporelogio_id_seq'::regclass), 'Design enigmático, com complicações exclusivas e acabamentos luxuosos.', 'MYSTIQUE');


INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Uma coleção icônica que redefine o design tradicional com sofisticação.', 'TANK COLLECTION');

INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Inspirada na elegância clássica, esta coleção simboliza inovação e estilo.', 'SANTOS COLLECTION');

INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Uma coleção que destaca formas arredondadas e fluidez no design.', 'BALLON BLEU COLLECTION');

INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Design ousado com uma abordagem moderna e elementos únicos.', 'PASHA COLLECTION');

INSERT INTO colecao (datainsert, dataupdate, id, descricao, nome)
VALUES (NOW(), NOW(), nextval('colecao_id_seq'::regclass), 'Estilo refinado e intemporal, simbolizando luxo e exclusividade.', 'PANTHÈRE COLLECTION');


INSERT INTO fabricante (nome, descricao, anoFundacao, localizacao)
VALUES ('MAISON LUMIÈRE', 'Um ateliê renomado pela criação de relógios com detalhes meticulosos, combinando inovação e tradição, representando o luxo francês.', '1888-01-28', 'Paris, França');

INSERT INTO fabricante (nome, descricao, anoFundacao, localizacao)
VALUES ('HORLOGERIE ÉLITE', 'Especialistas em relojoaria de alto padrão, conhecidos por seus mecanismos precisos e designs sofisticados, que evocam elegância atemporal.', '1977-11-09', 'New York, EUA');


INSERT INTO produto (nome, descricao, preco)
VALUES ('Relógio Casual Moderno', 'Relógio moderno com design elegante para uso diário.', 499.90)
RETURNING id;

-- Senha: 123456
INSERT INTO usuario (nome, cpf, email, login, senha, perfil) VALUES ('Gabriela User', '928.742.910-92', 'gabiuser.ra@gmail.com', 'gabiuser', '0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==', 'USER');
INSERT INTO usuario (nome, cpf, email, login, senha, perfil) VALUES ('Gilsiandry Admin', '111.111.111-11', 'giladmin@gmail.com','giladmin','0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==', 'ADMIN');

INSERT INTO telefone (numero, id_usuario) VALUES ('(011) 98456-7812', 1);
INSERT INTO telefone (numero, id_usuario) VALUES ('(061) 99901-5842', 1);
INSERT INTO telefone (numero, id_usuario) VALUES ('(061) 99933-0572', 1);
INSERT INTO telefone (numero, id_usuario) VALUES ('(069) 99933-0572', 2);


INSERT INTO relogio (
    id,
    anoLancamento,
    tipoPulseira,
    resistenciaAgua,
    temDiamante,
    id_tipo_relogio,
    fabricante_id,
    nomeImagem,
    nomeImagem1,
    nomeImagem2,
    nomeImagem3
) VALUES (
    1,        -- id herdado de Produto
    '2022-08-15',                  -- id do fabricante já existente
    1,
    TRUE,
    FALSE,
    1,
    1,
    'luxo.jpg',
    'luxo1.jpg',
    'luxo2.jpg',
    'luxo3.jpg'
);

INSERT INTO endereco (
    logradouro,
    bairro,
    numero,
    complemento,
    cep,
    id_cidade
) VALUES (
    'Rua das Palmeiras',
    'Centro',
    '123',
    'Apto 202',
    '77000-000',
    1 -- ID da cidade
);

INSERT INTO pagamento (
    confirmacaopagamento,
    dataconfirmacaopagamento,
    valor,
    datainsert,
    dataupdate,
    dtype
) VALUES (
    true,                     -- pagamento confirmado
    CURRENT_DATE,            -- data da confirmação
    199.90,                  -- valor do pagamento
    CURRENT_TIMESTAMP,       -- data de criação do registro
    CURRENT_TIMESTAMP,        -- data da última atualização
    'PIX'
);


INSERT INTO pedido (
    dataHoraPedido,
    totalPedido,
    id_usuario,
    id_pagamento,
    id_endereco
) VALUES (
    '2025-06-16 14:00:00',
    459.90,
    1,  -- ID do usuário
    1,  -- ID do pagamento
    1   -- ID do endereço
);


INSERT INTO itempedido (
    quantidade,
    preco,
    id_relogio,
    id_pedido,
    datainsert,
    dataupdate
) VALUES
(2, 129.90, 1, 1,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),   -- 2 unidades do produto 3
(1, 200.10, 1, 1,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);   -- 1 unidade do produto 5
