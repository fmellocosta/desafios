# Desafio 2: Crawler
## Instruções
### Requerimento
1. JDK 1.8
2. Maven 3.5+

### **IMPORTANTE**
Executar os passos abaixo na ordem, dado que o bot depende do crawler estar rodando.

### Passos para obter o código
1. Clonar o repositório para um diretório em seu computador
`$ git clone git@github.com:fmellocosta/desafios.git [DIRETORIO_NO_COMPUTADOR]`

### Passos para rodar a aplicação do Crawler
2. Entrar no diretório
`$ cd redditcrawler`
3. Gere o .jar 
`$ mvn clean install`
4. Execute o .jar gerado
`$ java -jar target/crawler-1.0.0.jar`

### Passos para rodar a aplicação do Bot
2. Entrar no diretório
`$ cd redditcrawlerbot`
3. Gere o .jar 
`$ mvn clean install`
4. Execute o .jar gerado
`$ java -jar target/redditcrawlerbot-1.0.0.jar`