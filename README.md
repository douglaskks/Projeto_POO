# Projeto_POO

# 💻 Projeto de POO - Sistema de Gestão de Restaurantes

* **Universidade:** Universidade Federal do Agreste de Pernambuco (UFAPE)
* **Disciplina:** Programação Orientada a Objetos
* **Professor:** Daliton da Silva
* **Alunos:**
    * Douglas Henrique Soares Salviano da Silva
    * João Victor Iane
    * Thiago José
    * Michael Raul Costa dos Santos

> **Status do Projeto:** 🚧 Em Desenvolvimento Final 🚧

Projeto acadêmico desenvolvido para a disciplina de **Programação Orientada a Objetos**.

---

## 📝 Descrição do Sistema

Este é um software de gestão interna que pode ser utilizado por qualquer restaurante. O sistema é operado via terminal (console) por funcionários para gerenciar as operações do dia a dia, como o cadastro de clientes, o gerenciamento de funcionários e a criação de pedidos.

O projeto foi desenvolvido com foco em aplicar os princípios da Programação Orientada a Objetos para criar uma arquitetura limpa e funcional.

---

## ✅ Funcionalidades

- [x] **Gerenciamento de Clientes**
  - [x] Cadastrar, buscar, listar e remover clientes.
  - [x] Adicionar um histórico simples de pedidos para cada cliente.
- [x] **Gerenciamento de Funcionários**
  - [x] Cadastrar, buscar, listar e remover funcionários.
- [ ] **Gerenciamento do Restaurante**
  - [ ] Configurar os dados do restaurante (nome, CNPJ).
  - [ ] Gerenciar o cardápio (adicionar e listar itens).
- [ ] **Gerenciamento de Pedidos**
  - [ ] Criar um novo pedido para um cliente, selecionando itens do cardápio.
  - [ ] Listar todos os pedidos feitos no sistema.
- [x] **Persistência de Dados**
  - [x] Os dados de clientes e funcionários são salvos em arquivos (`.dat`) e recarregados quando o sistema inicia.

---

## 🏛️ Arquitetura

O projeto foi estruturado em camadas para separar as responsabilidades, seguindo as boas práticas de design de software:

* **UI (Interface do Usuário):** A classe `MenuPrincipal`, responsável por toda a interação com o usuário no console.
* **Fachada (Façade):** A classe `SistemaFacade`, que serve como um ponto de entrada único para a camada de UI, orquestrando as operações e contendo as regras de negócio.
* **Dados:** Composta pelos pacotes:
    * `negocio`: Contém as classes de entidade (Model), como `Cliente` e `Funcionario`.
    * `dados`: Contém a camada de persistência, com a interface `IRepositorio` e a implementação `RepositorioSerializacao`.
    * `excecoes`: Contém as exceções customizadas para tratamento de erros.

---

