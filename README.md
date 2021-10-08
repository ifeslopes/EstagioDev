# Teste para vaga de estagio da Tunts:


Criar uma aplicação que deve ser capaz de ler uma planinha do Google sheets, buscar as informações necessárias, calcular e escrever o resultado na planilha.
REGRAS:
Calcular a situação de cada aluno baseando na média das 3 provas(P1,P2,P3) conforme a tabela:
Média(m) Situação
[ m < 5 ] --> Reprovado por Nota
[5<=m<7] --> Exame Final
[ m=>7 ] --> Aprovado

Caso o número de faltas ultrapasse 25% do número total de aulas o aluno terá a situação "Reprovado por Faltas", independente da média.

Caso a situação seja "Exame Final" é necessário calcular a "Nota para Aprovação Final"(naf) de cada aluno de acordo com seguinte

seguinte formula: 5<= (m+naf)/2

Caso a situação do aluno seja diferente de "Exame Final" preencha o campo "Nota para Aprovação Final" com 0

Minha opinião: achei muito legal porque aprendi fazer essa integração de uma aplicação com uma planilha do Google, tiver algumas dificuldade com os tokens mas valeu apena o aprendizado que tive.

O que foi utilizado apara realizar o desafio:
Linguagem: Java
Ide: intellij com Gradle para baixar as dependências do Google api.

Coloquei um pequeno menu para ter um pequena interação com usuário, onde 1 para fazer calculo 2 para mostra as informações do aluno no console e 0 para sair da aplicação.

Infelizmente não vei vaga de estagio quem sabe na próxima. :)
## Antes:
![Screenshot](https://github.com/ifeslopes/EstagioDev/blob/master/desaf0.png)
## Depois:
![Screenshot](https://github.com/ifeslopes/EstagioDev/blob/master/desaf1.png)
## Depois:
![Screenshot](https://github.com/ifeslopes/EstagioDev/blob/master/menu.png)
</p>
