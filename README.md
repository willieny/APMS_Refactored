# Sistema de Gestão de Produtividade Acadêmica

Projeto da disciplina de Projeto de Software (Refatorado)

## Sobre

Esta aplicação trata-se do refatoramento de um sistema de gestão de produtividade acadêmica no qual foi realizado o tratamento de exceções e a implementação de pelo menos dois padrões de projeto.

Para mais informações sobre o projeto mencionado => [Sistema de Gestão de Produtividade Acadêmica](https://github.com/willieny/Academic_Productivity_Management_System)

## Refatoramento

- [x] Tratamento de Exceções
- [x] Padrão de Projeto 1 [Extract Class]
- [x] Padrão de Projeto 2 [Extract Method]

## Extract Class

Quando uma classe faz o trabalho de duas, resulta em estranheza.

### Problema

A classe Menu possui o método clearScreen() que não condiz com a sua função.

![image](https://user-images.githubusercontent.com/32077255/104797929-07f85b00-57a1-11eb-80c7-e27c718dd9f8.png)

### Solução

Foi criada uma nova classe chamada Utility e em seguida adicionado o método clearScreen(). 
O método enter() foi obtido utilizando o padrão **Extract Method**.

![image](https://user-images.githubusercontent.com/32077255/104797909-dd0e0700-57a0-11eb-9ec6-e1df4b1b9204.png)

## Extract Method

Um fragmento de código que pode ser agrupado.

### Duplicate Code

Dois fragmentos de código parecem quase idênticos.

- Problema 1

O seguinte fragmento era encontrado em diversos métodos das classes ControllerCollaborator, ControllerProject e ControlerAcademicProduction:

```
System.out.println("Pressione ENTER para continuar.");
sc.nextLine();
```

- Solução 1

Foi criado um novo método chamado enter() e adicionado o fragmento de código anterior. Dessa forma, no local onde havia a duplicação de código foi feita uma chamada ao novo método. 

- Problema 2

Há duplicação de código, destinada a entrada de dados, nos métodos register() e editProjectInformation() da classe ControllerProject.

- Solução 2

Foi criado um novo método chamado createProject() e adicionado o fragmento de código duplicado. Dessa forma, no local onde havia a duplicação de código foi feita uma chamada ao novo método e o método editProjectInformation() precisou de algumas alterações nas demais linhas de código. 

### Long Method

Um método contém muitas linhas de código. Geralmente, qualquer método com mais de dez linhas deve levantar questionamentos.

- Problema 1

O método consultProject(), da classe ControllerProject, possuía cerca de 34 linhas de código. 

- Solução 1

Foram criados 2 métodos: printInConsultCollaborator(Project project) e printInConsultPublication(Project project) e fragmentos do método consultProject() foram adicionados a cada um. O mesmo procedimento foi realizado para o método **_consultCollaborator()_** da classe ControllerCollaborator.

- Problema 2

O método register(ControllerCollaborator controllerCollaborator), da classe ControllerAcademicProduction, possuía cerca de 36 linhas de código.

- Soluço 2

Foram criados 2 métodos: register_publication(ControllerCollaborator controllerCollaborator) e register_orientation(ControllerCollaborator controllerCollaborator) e fragmentos do método register(ControllerCollaborator controllerCollaborator) foram adicionados a cada um. O mesmo procedimento foi realizado para o método **_allocationOfAuthors(ControllerCollaborator controllerCollaborator)_** da mesma classe.

## Diagrama UML Refatorado

![Academic Productivity Management System vpd](https://user-images.githubusercontent.com/32077255/104078217-b4fc3200-51fb-11eb-8932-cf35df369e51.png)

## Nota

Aplicação foi executada pelo terminal e testada no sistema operacional Linux (Ubuntu 18.04). Dessa forma, o seguinte método poderá apresentar inconsistências nos demais sistemas.
```
public static void clearScreen() {
	System.out.println("\033[H\033[2J");
	System.out.flush();
}
