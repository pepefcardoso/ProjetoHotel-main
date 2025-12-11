SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Hotel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Hotel` DEFAULT CHARACTER SET utf8mb4 ;
USE `Hotel` ;

-- -----------------------------------------------------
-- Tabela `Hotel`.`caixa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`caixa` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`caixa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `valor_de_abertura` DECIMAL(10, 2) NOT NULL,
  `valor_de_fechamento` DECIMAL(10, 2) NOT NULL,
  `data_hora_abertura` DATETIME NOT NULL,
  `data_hora_fechamento` DATETIME NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  `funcionario_id` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `Hotel`.`quarto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`quarto` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`quarto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(100) NOT NULL,
  `capacidade_hospedes` INT NOT NULL,
  `metragem` FLOAT NOT NULL,
  `identificacao` VARCHAR(45) NOT NULL,
  `andar` INT NOT NULL,
  `flag_animais` BOOLEAN NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `Hotel`.`check_quarto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`check_quarto` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`check_quarto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_hora_inicio` DATETIME NOT NULL,
  `data_hora_fim` DATETIME NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  `quarto_id` INT NOT NULL,
  `check_id` INT NULL,
  `reserva_quarto_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_check_quarto_quarto1`
    FOREIGN KEY (`quarto_id`)
    REFERENCES `Hotel`.`quarto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_check_quarto_quarto1_idx` ON `Hotel`.`check_quarto` (`quarto_id` ASC) ;

-- -----------------------------------------------------
-- Tabela `Hotel`.`reserva`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`reserva` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`reserva` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_hora_reserva` DATETIME NOT NULL,
  `data_prevista_entrada` DATETIME NOT NULL,
  `data_prevista_saida` DATETIME NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  `check_id` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE INDEX `fk_reserva_check1_idx` ON `Hotel`.`reserva` (`check_id` ASC) ;


-- -----------------------------------------------------
-- Tabela `Hotel`.`check`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`check` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`check` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_hora_cadastro` DATETIME NOT NULL,
  `data_hora_entrada` DATETIME NOT NULL,
  `data_hora_saida` DATETIME NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  `check_quarto_id` INT NOT NULL,
  `reserva_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_check_check_quarto1`
    FOREIGN KEY (`check_quarto_id`)
    REFERENCES `Hotel`.`check_quarto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_check_reserva1` -- Nova FK
    FOREIGN KEY (`reserva_id`)
    REFERENCES `Hotel`.`reserva` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_check_check_quarto1_idx` ON `Hotel`.`check` (`check_quarto_id` ASC) ;
CREATE INDEX `fk_check_reserva1_idx` ON `Hotel`.`check` (`reserva_id` ASC) ;

ALTER TABLE `Hotel`.`check_quarto`
  ADD CONSTRAINT `fk_check_quarto_check1`
    FOREIGN KEY (`check_id`)
    REFERENCES `Hotel`.`check` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;

CREATE INDEX `fk_check_quarto_check1_idx` ON `Hotel`.`check_quarto` (`check_id` ASC) ;


-- -----------------------------------------------------
-- Tabela `Hotel`.`receber`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`receber` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`receber` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_hora_cadastro` DATETIME NOT NULL,
  `valor_original` DECIMAL(10, 2) NOT NULL,
  `desconto` DECIMAL(10, 2) NOT NULL,
  `acrescimo` DECIMAL(10, 2) NOT NULL,
  `valor_pago` DECIMAL(10, 2) NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  `check_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_receber_check1`
    FOREIGN KEY (`check_id`)
    REFERENCES `Hotel`.`check` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_receber_check1_idx` ON `Hotel`.`receber` (`check_id` ASC) ;


-- -----------------------------------------------------
-- Tabela `Hotel`.`movimento_caixa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`movimento_caixa` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`movimento_caixa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_hora_movimento` DATETIME NOT NULL,
  `valor` DECIMAL(10, 2) NOT NULL,
  `descricao` VARCHAR(100) NOT NULL,
  `obs` VARCHAR(100) NOT NULL,
  `status` CHAR(1) NOT NULL,
  `caixa_id` INT NOT NULL,
  `receber_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_movimento_caixa_caixa`
    FOREIGN KEY (`caixa_id`)
    REFERENCES `Hotel`.`caixa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movimento_caixa_receber1`
    FOREIGN KEY (`receber_id`)
    REFERENCES `Hotel`.`receber` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_movimento_caixa_caixa_idx` ON `Hotel`.`movimento_caixa` (`caixa_id` ASC) ;
CREATE INDEX `fk_movimento_caixa_receber1_idx` ON `Hotel`.`movimento_caixa` (`receber_id` ASC) ;

-- -----------------------------------------------------
-- Tabela `Hotel`.`marca`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`marca` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`marca` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(100) NOT NULL,
  `status` CHAR(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Tabela `Hotel`.`modelo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`modelo` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`modelo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(100) NOT NULL,
  `status` CHAR(1) NOT NULL,
  `marca_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_modelo_marca1`
    FOREIGN KEY (`marca_id`)
    REFERENCES `Hotel`.`marca` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_modelo_marca1_idx` ON `Hotel`.`modelo` (`marca_id` ASC) ;


-- -----------------------------------------------------
-- Tabela `Hotel`.`funcionario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`funcionario` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`funcionario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `fone` VARCHAR(255) NOT NULL,
  `fone2` VARCHAR(255) NULL,
  `email` VARCHAR(255) NOT NULL,
  `cep` VARCHAR(255) NOT NULL,
  `logradouro` VARCHAR(255) NOT NULL,
  `bairro` VARCHAR(255) NOT NULL,
  `cidade` VARCHAR(255) NOT NULL,
  `complemento` VARCHAR(255) NOT NULL,
  `data_cadastro` DATETIME NOT NULL,
  `cpf` VARCHAR(255) NOT NULL,
  `rg` VARCHAR(255) NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) DEFAULT 'A' NOT NULL,
  `usuario` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `sexo` CHAR(1) DEFAULT 'M',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Tabela `Hotel`.`fornecedor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`fornecedor` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`fornecedor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `fone` VARCHAR(255) NOT NULL,
  `fone2` VARCHAR(255) NULL,
  `email` VARCHAR(255) NOT NULL,
  `cep` VARCHAR(255) NOT NULL,
  `logradouro` VARCHAR(255) NOT NULL,
  `bairro` VARCHAR(255) NOT NULL,
  `cidade` VARCHAR(255) NOT NULL,
  `complemento` VARCHAR(255) NOT NULL,
  `data_cadastro` DATETIME NULL,
  `cpf` VARCHAR(255) NULL,
  `rg` VARCHAR(255) NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) DEFAULT 'A' NOT NULL,
  `razao_social` VARCHAR(255) NULL,
  `cnpj` VARCHAR(255) NULL,
  `inscricao_estadual` VARCHAR(255) NULL,
  `contato` VARCHAR(255) NULL,
  `sexo` CHAR(1) DEFAULT 'M',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `Hotel`.`hospede`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`hospede` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`hospede` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `fone` VARCHAR(255) NOT NULL,
  `fone2` VARCHAR(255) NULL,
  `email` VARCHAR(255) NOT NULL,
  `cep` VARCHAR(255) NOT NULL,
  `logradouro` VARCHAR(255) NOT NULL,
  `bairro` VARCHAR(255) NOT NULL,
  `cidade` VARCHAR(255) NOT NULL,
  `complemento` VARCHAR(255) NOT NULL,
  `data_cadastro` DATETIME NULL,
  `cpf` VARCHAR(255) NULL,
  `rg` VARCHAR(255) NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) DEFAULT 'A' NOT NULL,
  `razao_social` VARCHAR(255) NULL,
  `cnpj` VARCHAR(255) NULL,
  `inscricao_estadual` VARCHAR(255) NULL,
  `contato` VARCHAR(255) NULL,
  `sexo` CHAR(1) DEFAULT 'M',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Tabela `Hotel`.`veiculo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`veiculo` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`veiculo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `placa` VARCHAR(7) NOT NULL,
  `cor` VARCHAR(45) NOT NULL,
  `modelo_id` INT NOT NULL,
  `funcionario_id` INT NULL,
  `fornecedor_id` INT NULL,
  `hospede_id` INT NULL,
  `status` CHAR(1) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `chk_proprietario_unico`
    CHECK (
        (funcionario_id IS NOT NULL AND fornecedor_id IS NULL AND hospede_id IS NULL) OR
        (funcionario_id IS NULL AND fornecedor_id IS NOT NULL AND hospede_id IS NULL) OR
        (funcionario_id IS NULL AND fornecedor_id IS NULL AND hospede_id IS NOT NULL)
    ),
  CONSTRAINT `fk_veiculo_modelo1`
    FOREIGN KEY (`modelo_id`)
    REFERENCES `Hotel`.`modelo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_veiculo_funcionario1`
    FOREIGN KEY (`funcionario_id`)
    REFERENCES `Hotel`.`funcionario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_veiculo_fornecedor1`
    FOREIGN KEY (`fornecedor_id`)
    REFERENCES `Hotel`.`fornecedor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_veiculo_hospede1`
    FOREIGN KEY (`hospede_id`)
    REFERENCES `Hotel`.`hospede` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_veiculo_modelo1_idx` ON `Hotel`.`veiculo` (`modelo_id` ASC) ;
CREATE INDEX `fk_veiculo_funcionario1_idx` ON `Hotel`.`veiculo` (`funcionario_id` ASC) ;
CREATE INDEX `fk_veiculo_fornecedor1_idx` ON `Hotel`.`veiculo` (`fornecedor_id` ASC) ;
CREATE INDEX `fk_veiculo_hospede1_idx` ON `Hotel`.`veiculo` (`hospede_id` ASC) ;


-- -----------------------------------------------------
-- Tabela `Hotel`.`vaga_estacionamento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`vaga_estacionamento` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`vaga_estacionamento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(100) NOT NULL,
  `obs` TEXT NOT NULL,
  `metragem_vaga` FLOAT NOT NULL,
  `status` CHAR(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Tabela `Hotel`.`alocacao_vaga`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`alocacao_vaga` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`alocacao_vaga` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  `veiculo_id` INT NOT NULL,
  `vaga_estacionamento_id` INT NOT NULL,
  `check_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_alocacao_vaga_veiculo1`
    FOREIGN KEY (`veiculo_id`)
    REFERENCES `Hotel`.`veiculo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_alocacao_vaga_vaga_estacionamento1`
    FOREIGN KEY (`vaga_estacionamento_id`)
    REFERENCES `Hotel`.`vaga_estacionamento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_alocacao_vaga_check1`
    FOREIGN KEY (`check_id`)
    REFERENCES `Hotel`.`check` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_alocacao_vaga_veiculo1_idx` ON `Hotel`.`alocacao_vaga` (`veiculo_id` ASC) ;
CREATE INDEX `fk_alocacao_vaga_vaga_estacionamento1_idx` ON `Hotel`.`alocacao_vaga` (`vaga_estacionamento_id` ASC) ;
CREATE INDEX `fk_alocacao_vaga_check1_idx` ON `Hotel`.`alocacao_vaga` (`check_id` ASC) ;


-- -----------------------------------------------------
-- Tabela `Hotel`.`check_hospede`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`check_hospede` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`check_hospede` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo_hospede` VARCHAR(45) NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  `check_id` INT NOT NULL,
  `hospede_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_check_hospede_check1`
    FOREIGN KEY (`check_id`)
    REFERENCES `Hotel`.`check` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_check_hospede_hospede1`
    FOREIGN KEY (`hospede_id`)
    REFERENCES `Hotel`.`hospede` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_check_hospede_check1_idx` ON `Hotel`.`check_hospede` (`check_id` ASC) ;
CREATE INDEX `fk_check_hospede_hospede1_idx` ON `Hotel`.`check_hospede` (`hospede_id` ASC) ;


-- -----------------------------------------------------
-- Tabela `Hotel`.`reserva_quarto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`reserva_quarto` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`reserva_quarto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_hora_inicio` DATETIME NOT NULL,
  `data_hora_fim` DATETIME NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  `reserva_id` INT NOT NULL,
  `quarto_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_reserva_quarto_reserva1`
    FOREIGN KEY (`reserva_id`)
    REFERENCES `Hotel`.`reserva` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reserva_quarto_quarto1`
    FOREIGN KEY (`quarto_id`)
    REFERENCES `Hotel`.`quarto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_reserva_quarto_reserva1_idx` ON `Hotel`.`reserva_quarto` (`reserva_id` ASC) ;
CREATE INDEX `fk_reserva_quarto_quarto1_idx` ON `Hotel`.`reserva_quarto` (`quarto_id` ASC) ;

ALTER TABLE `Hotel`.`check_quarto`
  ADD CONSTRAINT `fk_check_quarto_reserva_quarto1`
    FOREIGN KEY (`reserva_quarto_id`)
    REFERENCES `Hotel`.`reserva_quarto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;

CREATE INDEX `fk_check_quarto_reserva_quarto1_idx` ON `Hotel`.`check_quarto` (`reserva_quarto_id` ASC) ;

-- -----------------------------------------------------
-- Tabela `Hotel`.`produto_copa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`produto_copa` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`produto_copa` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(100) NOT NULL,
  `valor` DECIMAL(10, 2) NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Tabela `Hotel`.`copa_quarto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`copa_quarto` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`copa_quarto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantidade` INT NOT NULL,
  `data_hora_pedido` DATETIME NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NOT NULL,
  `quarto_id` INT NOT NULL, 
  `produto_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_copa_quarto_quarto1`
    FOREIGN KEY (`quarto_id`)
    REFERENCES `Hotel`.`quarto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_copa_quarto_produto_copa1`
    FOREIGN KEY (`produto_id`)
    REFERENCES `Hotel`.`produto_copa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_copa_quarto_quarto1_idx` ON `Hotel`.`copa_quarto` (`quarto_id` ASC) ;
CREATE INDEX `fk_copa_quarto_produto_copa1_idx` ON `Hotel`.`copa_quarto` (`produto_id` ASC) ;


-- -----------------------------------------------------
-- Tabela `Hotel`.`servico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`servico` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`servico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(100) NOT NULL,
  `obs` TEXT NOT NULL,
  `status` CHAR(1) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Tabela `Hotel`.`ordem_servico`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Hotel`.`ordem_servico` ;

CREATE TABLE IF NOT EXISTS `Hotel`.`ordem_servico` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `data_hora_cadastro` DATETIME NULL,
  `data_hora_prevista_inicio` DATETIME NULL,
  `data_hora_prevista_termino` DATETIME NULL,
  `obs` TEXT NULL,
  `status` CHAR(1) NULL,
  `check_id` INT NULL,
  `servico_id` INT NULL,
  `quarto_id` INT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_ordem_servico_check1`
    FOREIGN KEY (`check_id`)
    REFERENCES `Hotel`.`check` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ordem_servico_servico1`
    FOREIGN KEY (`servico_id`)
    REFERENCES `Hotel`.`servico` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ordem_servico_quarto1`
    FOREIGN KEY (`quarto_id`)
    REFERENCES `Hotel`.`quarto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;

CREATE INDEX `fk_ordem_servico_check1_idx` ON `Hotel`.`ordem_servico` (`check_id` ASC) ;
CREATE INDEX `fk_ordem_servico_servico1_idx` ON `Hotel`.`ordem_servico` (`servico_id` ASC) ;
CREATE INDEX `fk_ordem_servico_quarto1_idx` ON `Hotel`.`ordem_servico` (`quarto_id` ASC) ;


DROP TRIGGER IF EXISTS `set_data_cadastro_funcionario`;
DELIMITER $$
CREATE TRIGGER `set_data_cadastro_funcionario` BEFORE INSERT ON `funcionario`
FOR EACH ROW
BEGIN
    SET NEW.data_cadastro = IFNULL(NEW.data_cadastro, NOW());
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `set_data_cadastro_hospede`;
DELIMITER $$
CREATE TRIGGER `set_data_cadastro_hospede` BEFORE INSERT ON `hospede`
FOR EACH ROW
BEGIN
    SET NEW.data_cadastro = IFNULL(NEW.data_cadastro, NOW());
END$$
DELIMITER ;

DROP TRIGGER IF EXISTS `set_data_cadastro_fornecedor`;
DELIMITER $$
CREATE TRIGGER `set_data_cadastro_fornecedor` BEFORE INSERT ON `fornecedor`
FOR EACH ROW
BEGIN
    SET NEW.data_cadastro = IFNULL(NEW.data_cadastro, NOW());
END$$
DELIMITER ;

ALTER TABLE `Hotel`.`caixa`
  ADD CONSTRAINT `fk_caixa_funcionario1`
    FOREIGN KEY (`funcionario_id`)
    REFERENCES `Hotel`.`funcionario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;

CREATE INDEX `fk_caixa_funcionario1_idx` ON `Hotel`.`caixa` (`funcionario_id` ASC) ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;