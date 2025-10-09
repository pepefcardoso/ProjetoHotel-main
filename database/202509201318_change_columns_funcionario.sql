ALTER TABLE funcionario
    MODIFY cep CHAR(8),
    MODIFY cpf CHAR(11),
    MODIFY fone CHAR(11),
    MODIFY fone2 CHAR(11) NULL,
    MODIFY rg VARCHAR(14) NULL,
    MODIFY data_cadastro DATE NULL,
    MODIFY status CHAR(1) DEFAULT 'A';

CREATE TRIGGER set_data_cadastro_funcionario
BEFORE INSERT ON funcionario
FOR EACH ROW
SET NEW.data_cadastro = IFNULL(NEW.data_cadastro, CURRENT_DATE);