# ValBets

ValBets é um aplicativo Android desenvolvido para gerenciar apostas em partidas esportivas utilizando criptomoedas na rede de testes Sepolia (Ethereum). O projeto inclui um contrato inteligente em Solidity para processar as apostas e um aplicativo desenvolvido em Kotlin para gerenciar as interações do usuário.

## 📋 Funcionalidades

- Listagem de partidas futuras e passadas.
- Possibilidade de apostar em um dos times utilizando Ethereum (ETH).
- Comunicação com um contrato inteligente na rede de testes Sepolia.
- Determinação do vencedor e distribuição automática dos prêmios aos apostadores vencedores.
- Integração com carteiras Ethereum utilizando Web3j.

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Solidity, Kotlin
- **Ferramentas**: Remix IDE, Infura
- **Rede**: Sepolia (Testnet Ethereum)
- **Bibliotecas**:
  - [Web3j](https://github.com/web3j/web3j) - Para integração com a blockchain Ethereum.
  - [Material Components for Android](https://material.io/develop/android) - Para criar interfaces modernas.

## 📝 Estrutura do Projeto

### Contrato Inteligente (`Betting.sol`)

O contrato inteligente permite:
1. Registrar apostas em dois times (`placeBetOnTeamA` e `placeBetOnTeamB`).
2. Declarar o vencedor (`declareWinner`).
3. Distribuir recompensas automaticamente aos apostadores vencedores.

### Aplicativo Android

O aplicativo possui:
- **Fragments** para exibir partidas futuras e passadas.
- **RecyclerView** para listar partidas com botões interativos.
- **Diálogo de aposta** para entrada de valores e seleção de times.
- Integração com a blockchain para envio de transações.

## 🚀 Como Executar o Projeto

### Pré-requisitos

1. **Contrato Inteligente**:
   - Instale [MetaMask](https://metamask.io/) e configure na rede de testes Sepolia.
   - Faça o deploy do contrato `Betting.sol` no Remix IDE ou outra ferramenta de sua escolha.
   - Anote o endereço do contrato para configurar no aplicativo.

2. **Aplicativo Android**:
   - Android Studio instalado.
   - Dispositivo Android ou emulador configurado.

### Configuração

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/valbets.git
   cd valbets

2. Configure o endereço do contrato e a chave privada no arquivo `MainActivity.kt`:
   ```kotlin
   val credentials = Credentials.create("<PRIVATE_KEY>")
   val contractAdress = "<CONTRACT_ADDRESS>"
   ```

3. Substitua o endpoint Infura no código:
   ```kotlin
   val web3j = Web3j.build(HttpService("https://sepolia.infura.io/v3/<YOUR_INFURA_PROJECT_ID>"))
   ```

4. Compile e execute o aplicativo no Android Studio.

## 🧪 Testando o Projeto

1. Configure uma carteira virtual com ETH na rede de testes Sepolia.
2. No aplicativo:
   - Navegue até as **Partidas Futuras**.
   - Clique em uma partida e insira o valor da aposta.
   - Escolha um time e envie a transação.
3. Verifique no contrato os valores recebidos e aguarde a finalização da partida.
4. O administrador (proprietário do contrato) pode declarar o vencedor no Remix IDE utilizando a função `declareWinner`.

## 💡 Melhorias Futuras

- Integração direta com carteiras como MetaMask.
- Criar contratos de partidas de forma automática.
- Criar partidas de forma automática usando uma API.
- Adquirir resultados de forma automática
- Melhorias na interface do usuário.
- Aplicar padrões de projeto.
- Adoção de bibliotecas de segurança para o armazenamento de credenciais.

Obs: Este projeto NÃO se encontra finalizado, ainda há diversos ajustes a serem feitos como refatoração, aplicar padrão de projeto, ajustes em nomes de variáveis, classes e em lógicas, etc.

![print1](https://github.com/user-attachments/assets/0a1e16a5-ba7f-4e4f-aa72-218589f2414a)
![print2](https://github.com/user-attachments/assets/0720bb1a-0f4d-4a91-8dc3-169c469c7199)
![image](https://github.com/user-attachments/assets/9dc1726f-88b2-458b-853c-a6f8ba014fe4)
