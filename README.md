## Fluxo Check-in

1. Menu Movimentos → Check-in/Check-out
2. Clicar "Novo" (ativa aba Check-in)
3. Opcionalmente selecionar Reserva (pré-preenche datas)
4. Clicar ícone de busca do Hóspede → selecionar hóspede
5. Preencher Data de Entrada
6. Clicar ícone de busca do Quarto → selecionar quarto
7. Opcionalmente selecionar Veículo e Vaga
8. Clicar "Gravar"
   - Cria CheckQuarto → Check → CheckHospede
   - Se veiculo+vaga: cria AlocacaoVaga
   - Se reserva: vincula reserva ao check e atualiza status para 'F'

## Fluxo Check-out

1. Na aba "Check-out", clicar ícone "Buscar Hóspede"
2. Selecionar o check-in ativo da lista
3. Preencher Data de Saída
4. Preencher Valor Pago
5. Selecionar Status do Recebimento
6. Clicar "Gravar"
   - Atualiza Check status → 'F'
   - Atualiza CheckQuarto status → 'F'
   - Cria Receber

## Fluxo Reserva

1. Menu Movimentos → Reserva
2. Clicar "Novo"
3. Opcionalmente buscar e selecionar Hóspede (campo de exibição)
4. Preencher Previsão Entrada e Saída
5. Preencher Observações
6. Clicar "Gravar"
