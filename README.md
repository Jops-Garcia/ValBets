# ValBets - Aplicativo de Apostas em Partidas Usando Ethereum
Este é um projeto de aplicativo Android que permite ao usuário apostar em partidas esportivas com criptomoedas, usando a rede Ethereum. O projeto utiliza um contrato inteligente desenvolvido com Solidity e a biblioteca Web3j para integração com a blockchain, especificamente em uma rede de testes (Sepolia).

# Funcionalidades
Listagem de Partidas: Mostra uma lista de partidas disponíveis para apostas.
Apostas com Criptomoedas: O usuário pode fazer apostas usando Ethereum.
Declaração do Vencedor: Após o término do jogo, o vencedor é declarado, e os pagamentos são realizados automaticamente com base nas apostas.
Integração com MetaMask: Utiliza a carteira virtual MetaMask para gerenciar o Ethereum do usuário e permitir transações seguras.
Estrutura do Projeto
Android App (Java/Kotlin): Interface principal do aplicativo.
Web3j: Biblioteca para interagir com o contrato inteligente na blockchain Ethereum.
Smart Contract (Solidity): Contrato inteligente que gerencia as apostas, armazenamento de dados e declara o vencedor automaticamente.
