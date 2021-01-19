# Sistema de Gestão de Produtividade Acadêmica

Projeto da disciplina de Projeto de Software (Refatorado)

## Sobre

Esta aplicação trata-se do refatoramento de um sistema de gestão de produtividade acadêmica no qual foi realizado o tratamento de exceções e a implementação de pelo menos dois padrões de projeto.

Para mais informações sobre o projeto mencionado: [Sistema de Gestão de Produtividade Acadêmica](https://github.com/willieny/Academic_Productivity_Management_System)

## Refatoramento

- [x] Tratamento de Exceções
- [x] Padrão de Projeto 1 [Singleton]
- [x] Padrão de Projeto 2 [Facade]

## Singleton

O **Singleton** é um padrão de projeto criacional que garante que uma classe tenha apenas uma instância, enquanto provê um ponto de acesso global para essa instância.

Foi implementado nas classes ControllerCollaborator, ControllerProject e ControllerAcademicProduction.

## Facade

O **Facade** é um padrão de projeto estrutural que fornece uma interface simplificada para uma biblioteca, um framework, ou qualquer conjunto complexo de classes.

Para implementar este padrão foi criada a classe ManagementSystem. Esta classe funciona como uma fachada, ela provê dados do pacote controle para o pacote aplicação sem externalizar os dados e reduzindo a complexidade.

## Nota

Aplicação foi executada pelo terminal e testada no sistema operacional Linux (Ubuntu 18.04). Dessa forma, o seguinte método poderá apresentar inconsistências nos demais sistemas.
```
public static void clearScreen() {
	System.out.println("\033[H\033[2J");
	System.out.flush();
}
```
