-- DEFINIÇÃO DOS DADOS MESTRES/BÁSICOS
INSERT INTO Hotel.marca (descricao, status) VALUES 
('Toyota', 'A'),
('Honda', 'A'),
('Volkswagen', 'A');

INSERT INTO Hotel.modelo (descricao, status, marca_id) VALUES 
('Corolla', 'A', 1),
('Civic', 'A', 2),
('Golf', 'A', 3);

INSERT INTO Hotel.funcionario (nome, fone, fone2, email, cep, logradouro, bairro, cidade, complemento, data_cadastro, cpf, rg, obs, status, usuario, senha, sexo) VALUES 
('João Silva', '999999999', NULL, 'joao.silva@hotel.com', '12345678', 'Rua A', 'Centro', 'Cidade 1', 'Apto 101', NOW(), '11122233344', '1234567', 'Atendente do Check-in', 'A', 'joao.s', 'senha123', 'M'),
('Maria Souza', '888888888', NULL, 'maria.souza@hotel.com', '23456789', 'Avenida B', 'Bairro 2', 'Cidade 1', 'Casa', NOW(), '22233344455', '2345678', 'Gerente', 'A', 'maria.s', 'senha123', 'F'),
('Carlos Pereira', '777777777', NULL, 'carlos.pereira@hotel.com', '34567890', 'Rua C', 'Bairro 3', 'Cidade 2', 'Sala 5', NOW(), '33344455566', '3456789', 'Manutenção', 'A', 'carlos.p', 'senha123', 'M');

INSERT INTO Hotel.fornecedor (nome, fone, fone2, email, cep, logradouro, bairro, cidade, complemento, data_cadastro, cpf, rg, obs, status, razao_social, cnpj, inscricao_estadual, contato, sexo) VALUES 
('Fornecedor Limpeza', '666666666', NULL, 'contato@limpeza.com', '45678901', 'Rua D', 'Industrial', 'Cidade 3', 'Galpão', NOW(), NULL, NULL, 'Produtos de Limpeza', 'A', 'Limpeza Ltda', '11111111000111', '12345', 'Ana', 'F'),
('Fornecedor Alimentos', '555555555', NULL, 'contato@alimentos.com', '56789012', 'Avenida E', 'Comercial', 'Cidade 4', 'Escritório', NOW(), NULL, NULL, 'Alimentos e Bebidas', 'A', 'Alimentos S/A', '22222222000122', '54321', 'Bruno', 'M');

INSERT INTO Hotel.hospede (nome, fone, fone2, email, cep, logradouro, bairro, cidade, complemento, data_cadastro, cpf, rg, obs, status, razao_social, cnpj, inscricao_estadual, contato, sexo) VALUES 
('Ana Costa', '444444444', NULL, 'ana.costa@email.com', '67890123', 'Rua F', 'Jardim', 'Cidade 1', 'Apto 202', NOW(), '44455566677', '4567890', 'Hóspede frequente', 'A', NULL, NULL, NULL, NULL, 'F'),
('Pedro Santos', '333333333', NULL, 'pedro.santos@email.com', '78901234', 'Avenida G', 'Praia', 'Cidade 5', 'Cobertura', NOW(), '55566677788', '5678901', 'Novo hóspede', 'A', NULL, NULL, NULL, NULL, 'M'),
('Empresa ABC', '222222222', NULL, 'reservas@abc.com', '89012345', 'Rua H', 'Centro', 'Cidade 6', 'Sede', NOW(), NULL, NULL, 'Reserva corporativa', 'A', 'Empresa ABC S.A.', '33333333000133', '98765', 'Gerente de Viagens', 'N');

INSERT INTO Hotel.quarto (descricao, capacidade_hospedes, metragem, identificacao, andar, flag_animais, obs, status) VALUES 
('Standard Casal', 2, 25.5, '101', 1, FALSE, 'Vista para a piscina', 'L'),
('Luxo Familiar', 4, 45.0, '205', 2, TRUE, 'Com banheira de hidromassagem', 'O'),
('Standard Solteiro', 1, 15.0, '302', 3, FALSE, 'Próximo ao elevador', 'M');

INSERT INTO Hotel.vaga_estacionamento (descricao, obs, metragem_vaga, status) VALUES 
('Vaga 01', 'Próxima à recepção', 12.0, 'L'),
('Vaga 02', 'Coberta', 15.0, 'O');

INSERT INTO Hotel.servico (descricao, obs, status) VALUES 
('Lavanderia Rápida', 'Entrega em 24h', 'A'),
('Passeio Turístico', 'City tour de 4h', 'A');

INSERT INTO Hotel.produto_copa (descricao, valor, obs, status) VALUES 
('Água Mineral', 5.00, 'Garrafa 500ml', 'A'),
('Refrigerante Lata', 8.50, 'Lata 350ml', 'A');

-- DEFINIÇÃO DOS DADOS DEPENDENTES (VEÍCULO)
INSERT INTO Hotel.veiculo (placa, cor, modelo_id, funcionario_id, fornecedor_id, hospede_id, status) VALUES 
('ABC1234', 'Preto', 1, NULL, NULL, 1, 'A'), -- Carro do Hospede Ana Costa
('XYZ5678', 'Branco', 2, 1, NULL, NULL, 'A'), -- Carro do Funcionario João Silva
('DEF9012', 'Vermelho', 3, NULL, 1, NULL, 'A'); -- Carro do Fornecedor Limpeza

-- DEFINIÇÃO DO FLUXO DE RESERVA E CHECK-IN (Fase 1)
INSERT INTO Hotel.reserva (data_hora_reserva, data_prevista_entrada, data_prevista_saida, obs, status, check_id) VALUES 
(NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 5 DAY), 'Reserva de férias', 'C', NULL), -- Reserva CANCELADA
(NOW(), DATE_ADD(NOW(), INTERVAL 10 DAY), DATE_ADD(NOW(), INTERVAL 12 DAY), 'Reserva futura', 'A', NULL), -- Reserva ATIVA
(NOW(), DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY), 'Reserva que virou Check-in', 'F', NULL); -- Reserva EFETUADA (finalizada)

INSERT INTO Hotel.reserva_quarto (data_hora_inicio, data_hora_fim, obs, status, reserva_id, quarto_id) VALUES 
(DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 5 DAY), 'Quarto 101', 'C', 1, 1), -- Reserva cancelada para Quarto 101
(DATE_ADD(NOW(), INTERVAL 10 DAY), DATE_ADD(NOW(), INTERVAL 12 DAY), 'Quarto 205', 'A', 2, 2), -- Reserva ativa para Quarto 205
(DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_ADD(NOW(), INTERVAL 2 DAY), 'Quarto 302', 'F', 3, 3); -- Reserva efetuada para Quarto 302

INSERT INTO Hotel.caixa (valor_de_abertura, valor_de_fechamento, data_hora_abertura, data_hora_fechamento, obs, status, funcionario_id) VALUES 
(100.00, 150.00, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 HOUR), 'Caixa do dia anterior', 'F', 1),
(50.00, 0.00, NOW(), NOW(), 'Caixa aberto hoje', 'A', 2);

-- DEFINIÇÃO DO FLUXO DE RESERVA E CHECK-IN (Fase 2)
INSERT INTO Hotel.check_quarto (data_hora_inicio, data_hora_fim, obs, status, quarto_id, check_id, reserva_quarto_id) VALUES 
(DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 HOUR), 'Check-out finalizado', 'F', 3, NULL, 3); -- Quarto 302, status 'F' (Finalizado)

INSERT INTO Hotel.check (data_hora_cadastro, data_hora_entrada, data_hora_saida, obs, status, check_quarto_id, reserva_id) VALUES 
(DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 HOUR), 'Check-in finalizado', 'F', 1, 3); -- Check-in do Hospede Pedro Santos

-- ATUALIZAÇÃO DA FK DE RESERVA E CHECK_QUARTO (Cadeia de dependência cíclica)
UPDATE Hotel.reserva SET check_id = 1 WHERE id = 3;
UPDATE Hotel.check_quarto SET check_id = 1 WHERE id = 1;


-- DEFINIÇÃO DE MOVIMENTO E ASSOCIAÇÕES
INSERT INTO Hotel.receber (data_hora_cadastro, valor_original, desconto, acrescimo, valor_pago, obs, status, check_id) VALUES 
(DATE_SUB(NOW(), INTERVAL 1 HOUR), 1000.00, 50.00, 0.00, 950.00, 'Pagamento do check-out', 'P', 1);

INSERT INTO Hotel.movimento_caixa (data_hora_movimento, valor, descricao, obs, status, caixa_id, receber_id) VALUES 
(DATE_SUB(NOW(), INTERVAL 1 HOUR), 950.00, 'Recebimento Check-out #1', 'Pagamento via cartão', 'A', 1, 1);

INSERT INTO Hotel.alocacao_vaga (obs, status, veiculo_id, vaga_estacionamento_id, check_id) VALUES 
('Veículo do hóspede no check-in', 'F', 1, 2, 1);

INSERT INTO Hotel.check_hospede (tipo_hospede, obs, status, check_id, hospede_id) VALUES 
('Responsável', 'Hóspede principal da reserva', 'A', 1, 2); -- Pedro Santos é o hospede 2

INSERT INTO Hotel.copa_quarto (quantidade, data_hora_pedido, obs, status, quarto_id, produto_id) VALUES 
(2, DATE_SUB(NOW(), INTERVAL 1 DAY), 'Pedido no quarto', 'F', 3, 1), -- 2 águas no Quarto 302
(1, DATE_SUB(NOW(), INTERVAL 1 DAY), 'Pedido no quarto', 'F', 3, 2); -- 1 refrigerante no Quarto 302

INSERT INTO Hotel.ordem_servico (data_hora_cadastro, data_hora_prevista_inicio, data_hora_prevista_termino, obs, status, check_id, servico_id, quarto_id) VALUES 
(NOW(), DATE_ADD(NOW(), INTERVAL 1 HOUR), DATE_ADD(NOW(), INTERVAL 2 HOUR), 'Solicitação de serviço de lavanderia', 'A', 1, 1, 3);