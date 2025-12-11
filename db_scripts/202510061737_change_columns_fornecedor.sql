ALTER TABLE fornecedor
    MODIFY cep CHAR(8),
    MODIFY fone CHAR(11),
    MODIFY fone2 CHAR(11) NULL,
    MODIFY rg VARCHAR(14) NULL,
    MODIFY cpf CHAR(11) NULL,
    MODIFY cnpj CHAR(14) NULL,
    MODIFY razao_social VARCHAR(100) NULL,
    MODIFY contato VARCHAR(100) NULL,
    MODIFY inscricao_estadual VARCHAR(15) NULL,
    MODIFY data_cadastro DATE NULL,
    MODIFY status CHAR(1) DEFAULT 'A',
    DROP COLUMN usuario,
    DROP COLUMN senha;

CREATE TRIGGER set_data_cadastro_fornecedor
BEFORE INSERT ON fornecedor
FOR EACH ROW
SET NEW.data_cadastro = IFNULL(NEW.data_cadastro, CURRENT_DATE);

ALTER TABLE alocacao_vaga MODIFY COLUMN status CHAR(1);
ALTER TABLE vaga_estacionamento MODIFY COLUMN status CHAR(1);
ALTER TABLE caixa MODIFY COLUMN status CHAR(1);

ALTER TABLE caixa ADD COLUMN valor_abertura FLOAT;
ALTER TABLE caixa ADD COLUMN valor_fechamento FLOAT;

ALTER TABLE caixa ADD COLUMN funcionario_id INT;

ALTER TABLE `check` ADD COLUMN reserva_id INT;

ALTER TABLE check_hospede MODIFY COLUMN status CHAR(1);

ALTER TABLE check_quarto MODIFY COLUMN status CHAR(1);

ALTER TABLE check_quarto ADD COLUMN check_id INT;

ALTER TABLE check_quarto ADD COLUMN reserva_quarto_id INT;

ALTER TABLE copa_quarto MODIFY COLUMN quantidade INT;

ALTER TABLE copa_quarto MODIFY COLUMN status CHAR(1);

ALTER TABLE copa_quarto ADD COLUMN produto_id INT;

ALTER TABLE fornecedor MODIFY COLUMN cep VARCHAR(255);

ALTER TABLE fornecedor MODIFY COLUMN cpf VARCHAR(255);

ALTER TABLE fornecedor MODIFY COLUMN data_cadastro VARCHAR(255);

-- Ajusta colunas de texto (String) para VARCHAR(255)
ALTER TABLE fornecedor MODIFY COLUMN nome VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN fone VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN fone2 VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN email VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN cep VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN logradouro VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN bairro VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN cidade VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN complemento VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN data_cadastro VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN cpf VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN rg VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN razao_social VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN cnpj VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN inscricao_estadual VARCHAR(255);
ALTER TABLE fornecedor MODIFY COLUMN contato VARCHAR(255);
-- A coluna 'obs' é mapeada como TEXT no modelo Pessoa
ALTER TABLE fornecedor MODIFY COLUMN obs TEXT;

-- Ajusta colunas de texto (String) para VARCHAR(255)
ALTER TABLE hospede MODIFY COLUMN nome VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN fone VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN fone2 VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN email VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN cep VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN logradouro VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN bairro VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN cidade VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN complemento VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN data_cadastro VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN cpf VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN rg VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN razao_social VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN cnpj VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN inscricao_estadual VARCHAR(255);
ALTER TABLE hospede MODIFY COLUMN contato VARCHAR(255);
-- A coluna 'obs' é mapeada como TEXT no modelo Pessoa
ALTER TABLE hospede MODIFY COLUMN obs TEXT;

-- Ajusta colunas de texto (String) para VARCHAR(255)
ALTER TABLE funcionario MODIFY COLUMN nome VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN fone VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN fone2 VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN email VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN cep VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN logradouro VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN bairro VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN cidade VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN complemento VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN data_cadastro VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN cpf VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN rg VARCHAR(255);
-- Colunas próprias de Funcionario
ALTER TABLE funcionario MODIFY COLUMN usuario VARCHAR(255);
ALTER TABLE funcionario MODIFY COLUMN senha VARCHAR(255);
-- A coluna 'obs' é mapeada como TEXT no modelo Pessoa
ALTER TABLE funcionario MODIFY COLUMN obs TEXT;

-- Tabelas herdadas de Pessoa (Fornecedor, Hospede, Funcionario)
ALTER TABLE fornecedor MODIFY COLUMN status CHAR(1);
ALTER TABLE hospede MODIFY COLUMN status CHAR(1);
ALTER TABLE funcionario MODIFY COLUMN status CHAR(1);

-- Tabelas de cadastro simples
ALTER TABLE marca MODIFY COLUMN status CHAR(1);
ALTER TABLE modelo MODIFY COLUMN status CHAR(1);
ALTER TABLE produto_copa MODIFY COLUMN status CHAR(1); -- Produto (Produto.java)
ALTER TABLE servico MODIFY COLUMN status CHAR(1);
ALTER TABLE vaga_estacionamento MODIFY COLUMN status CHAR(1);
ALTER TABLE veiculo MODIFY COLUMN status CHAR(1);
ALTER TABLE quarto MODIFY COLUMN status CHAR(1);

-- Tabelas de movimentos e relacionamentos
ALTER TABLE alocacao_vaga MODIFY COLUMN status CHAR(1);
ALTER TABLE caixa MODIFY COLUMN status CHAR(1);
ALTER TABLE check_hospede MODIFY COLUMN status CHAR(1);
ALTER TABLE check_quarto MODIFY COLUMN status CHAR(1);
ALTER TABLE copa_quarto MODIFY COLUMN status CHAR(1);
ALTER TABLE movimento_caixa MODIFY COLUMN status CHAR(1);
ALTER TABLE ordem_servico MODIFY COLUMN status CHAR(1); -- OrdemServico (OrdemServico.java)
ALTER TABLE receber MODIFY COLUMN status CHAR(1);
ALTER TABLE reserva MODIFY COLUMN status CHAR(1);
ALTER TABLE reserva_quarto MODIFY COLUMN status CHAR(1);

CREATE TABLE ordem_servico (
    id INT PRIMARY KEY AUTO_INCREMENT,
    data_hora_cadastro DATETIME,
    data_hora_prevista_inicio DATETIME,
    data_hora_prevista_termino DATETIME,
    obs TEXT,
    status CHAR(1),
    check_id INT,
    servico_id INT,
    quarto_id INT,
    -- Restrições de Chave Estrangeira (Opcional, mas recomendado)
    FOREIGN KEY (check_id) REFERENCES `check`(id),
    FOREIGN KEY (servico_id) REFERENCES servico(id),
    FOREIGN KEY (quarto_id) REFERENCES quarto(id)
);

ALTER TABLE quarto MODIFY COLUMN flag_animais BOOLEAN;

ALTER TABLE receber MODIFY COLUMN status CHAR(1);

-- Corrige a coluna de entrada
ALTER TABLE reserva MODIFY COLUMN data_prevista_entrada DATETIME;

-- Corrige a coluna de saída
ALTER TABLE reserva MODIFY COLUMN data_prevista_saida DATETIME;

-- Corrige a coluna de cadastro/reserva
ALTER TABLE reserva MODIFY COLUMN data_hora_reserva DATETIME;

-- 1. Tabela RESERVA (Reserva.java)
ALTER TABLE reserva MODIFY COLUMN data_hora_reserva DATETIME;
ALTER TABLE reserva MODIFY COLUMN data_prevista_entrada DATETIME;
ALTER TABLE reserva MODIFY COLUMN data_prevista_saida DATETIME;

-- 2. Tabela RESERVA_QUARTO (ReservaQuarto.java)
ALTER TABLE reserva_quarto MODIFY COLUMN data_hora_inicio DATETIME;
ALTER TABLE reserva_quarto MODIFY COLUMN data_hora_fim DATETIME;

-- 3. Tabela CHECK (Check.java) - Usando backticks para a palavra reservada 'check'
ALTER TABLE `check` MODIFY COLUMN data_hora_cadastro DATETIME;
ALTER TABLE `check` MODIFY COLUMN data_hora_entrada DATETIME;
ALTER TABLE `check` MODIFY COLUMN data_hora_saida DATETIME;

-- 4. Tabela CHECK_QUARTO (CheckQuarto.java)
ALTER TABLE check_quarto MODIFY COLUMN data_hora_inicio DATETIME;
ALTER TABLE check_quarto MODIFY COLUMN data_hora_fim DATETIME;

-- 5. Tabela CAIXA (Caixa.java)
ALTER TABLE caixa MODIFY COLUMN data_hora_abertura DATETIME;
ALTER TABLE caixa MODIFY COLUMN data_hora_fechamento DATETIME;

-- 6. Tabela MOVIMENTO_CAIXA (MovimentoCaixa.java)
ALTER TABLE movimento_caixa MODIFY COLUMN data_hora_movimento DATETIME;

-- 7. Tabela RECEBER (Receber.java)
ALTER TABLE receber MODIFY COLUMN data_hora_cadastro DATETIME;

-- 8. Tabela ORDEM_SERVICO (OrdemServico.java) - Assumindo o nome correto 'ordem_servico'
ALTER TABLE ordem_servico MODIFY COLUMN data_hora_cadastro DATETIME;
ALTER TABLE ordem_servico MODIFY COLUMN data_hora_prevista_inicio DATETIME;
ALTER TABLE ordem_servico MODIFY COLUMN data_hora_prevista_termino DATETIME;

-- 9. Tabela COPA_QUARTO (CopaQuarto.java)
ALTER TABLE copa_quarto MODIFY COLUMN data_hora_pedido DATETIME;

ALTER TABLE reserva MODIFY COLUMN status CHAR(1);