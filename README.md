# Sistema de Gestão de Produtividade Acadêmica

Projeto da disciplina de Projeto de Software (Projeto OO)

## Sobre

Esta aplicação trata-se do refatoramento de um sistema de gestão de produtividade acadêmica no qual foi realizado o tratamento de exceções e a implementação de pelo menos dois padrões de projeto.
Para mais informações sobre o projeto mencionado => [Sistema de Gestão de Produtividade Acadêmica](https://github.com/willieny/Academic_Productivity_Management_System)

## Refatoramento

- [x] Tratamento de Exceções
- [x] Padrão de Projeto 1 [Extract Class]
- [x] Padrão de Projeto 2 [Extract Method]

### Extract Class

Quando uma classe faz o trabalho de duas, resulta em estranheza.

#### Problema

![image](https://user-images.githubusercontent.com/32077255/104792467-509e1d00-577d-11eb-921e-0019c23dac04.png)

A classe Menu possui o método clearScreen() que não condiz com a sua função.

#### Solução

![image](https://user-images.githubusercontent.com/32077255/104792436-2c424080-577d-11eb-94d7-7fb83bc120e3.png)

Foi criada uma nova classe chamada Utility e em seguida adicionado o método clearScreen(). 
O método enter() foi obtido utilizando o padrão **Extract Method**.

### Extract Method

Você tem um fragmento de código que pode ser agrupado.

- Duplicação de Código

#### Problema 1

O seguinte fragmento era encontrado em diversos métodos das classes ControllerCollaborator, ControllerProject e ControlerAcademicProduction:

'''
System.out.println("Pressione ENTER para continuar.");
sc.nextLine();
'''

#### Solução 1

Foi criado um novo método chamado enter() e adicionado o fragmento de código anterior. Dessa forma, no local onde havia a duplicação de código foi feita uma chamada ao novo método. 

#### Problema 2

Os métodos register() e editProjectInformation() têm uma parte das suas linhas de código destinadas a ler dados de entrada. Contudo, essas linhas de código são iguais. 

#### Solução 2

Foi criado um novo método chamado createProject() e adicionado o fragmento de código duplicado. Dessa forma, no local onde havia a duplicação de código foi feita uma chamada ao novo método e o método editProjectInformation() precisou de algumas alterações nas demais linhas de código. 

- Long Method

#### Problema 1

#### Solução 1


## Diagrama UML

![Academic Productivity Management System vpd](https://user-images.githubusercontent.com/32077255/104078217-b4fc3200-51fb-11eb-8932-cf35df369e51.png)

## Nota

Aplicação foi executada pelo terminal e testada no sistema operacional Linux (Ubuntu 18.04). Dessa forma, o seguinte método poderá apresentar inconsistências nos demais sistemas.
```
public static void clearScreen() {
	System.out.println("\033[H\033[2J");
	System.out.flush();
}
