
CREATE DATABASE IF NOT EXISTS agenda_telefonica
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE agenda_telefonica;


DROP TABLE IF EXISTS contato;

-- Tabela de contatos.
-- id       -> chave primaria, gerada automaticamente.
-- nome     -> nome do contato (obrigatorio).
-- telefone -> numero de telefone.
-- email    -> endereco de e-mail.
CREATE TABLE contato (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email    VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dados de exemplo (banco populado).
INSERT INTO contato (nome, telefone, email) VALUES
    ('Ana Souza',      '(62) 99876-1122', 'ana.souza@email.com'),
    ('Bruno Carvalho', '(62) 98123-4567', 'bruno.carvalho@email.com'),
    ('Carla Mendes',   '(11) 99654-3210', 'carla.mendes@email.com'),
    ('Diego Lima',     '(21) 98765-4321', 'diego.lima@email.com'),
    ('Eduarda Rocha',  '(62) 99111-2233', 'eduarda.rocha@email.com');

-- Consulta de conferencia.
SELECT * FROM contato;
