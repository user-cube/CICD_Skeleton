# Smart Room

A Smart Room é um projeto que se propõe a criar salas inteligentes nas quais será possível monitorização de vários aspetos que vão desde o controlo de acessos às salas, níveis de temperatura a medições CO2 e humidade.

Aliado a uma dashboard todos os dados podem ser analisados em gráficos intuitivos que permitem melhor compreender o que se passa na sala, além disso será possível definir algumas preferências do utilizador em relação ao ambiente, podendo personalizar que alertas quer receber, bem como definir que acessos podem ser realizados na sala uma vez que será possível gerir quais os cartões autorizados ou não.

## Autores
* **Team Manager**  - [Rui Coelho](https://github.com/user-cube)
* **Product Owner**  - [Jean Brito](https://github.com/JoelBrito13)
* **Architect** - [Luís Costa](https://github.com/lmcosta98)
* **DevOps master** - [Pedro Candoso](https://github.com/PBCandoso)

## Repositórios de referência:
* [Sensores](https://github.com/user-cube/Smart_Room)
* [Frontend](https://github.com/user-cube/ies_frontend)
* [Backend](https://github.com/user-cube/ies_api)
* [NFC Controller](https://github.com/user-cube/door_control_center)

## Justificação da utilização de vários repositórios
Tal como foi definido na arquitetura do projeto, dissemos que íamos criar uma pwa baseada na aplicação web, para a criação de uma pwa, a Google exige:
* Seja acessível nos seus servidores de criação
* Esteja em HTTPS
* Quando feita em react usar a versão `production build`

Para que isto seja feito, um container na VM que nos foi fornecida não permite que a mesma seja criada. De modo a alcançar estes requisitos colocamos todo o nosso código no Heroku, assim, podemos fazer deploy do mesmo lá fornecendo os serviços que precisamos.
Dado as especificidade do Heroku, o deploy é mais rápido e simples quando o código se encontra em repositórios separados, dái termos tantos repositórios.

### CI/CD
Existem mecanismos de CI/CD implementados.
No que diz respeito a CI, foi feito integração com o Github Actions que trata de todo o processo de CI.
O CD ficou a cargo do Heroku que, após o Github Actions dar um parecer positivo, inicia o seu processo de deployment.

### Cloud AMQP
Uma vez que colocamos todos os serviços fora da UA, tivemos de recorrer ao alojamento do rabbit também fora da UA, para isso usamos a Cloud AMQP serviço fornecido pelos criadores do RabbitMQ.

### Mongo Atlas
Da mesma forma que tivemos de colocar o Rabbit fora da UA, fizemos o mesmo com o MongoDB, passando assim o Mongo para o Mongo Atlas serviço fornecido pelos criadores do mesmo..

### Deploy
Como foi mencionado anteriormente, colocamos tudo fora da UA, deste modo os serviços podem acedidos em:
* **Frontend** - [Heroku](https://iesfrontend.herokuapp.com/)
* **SpringBoot** - [Heroku](https://iesapi.herokuapp.com/)
* **Flask API** - [Heroku](https://ies-controller.herokuapp.com/)

**Nota**: A API não pode ser acedida no / uma vez que não tem um caminho definido para o mesmo, de qualquer modo, pode ser consultada a parte da [documentação](https://iesapi.herokuapp.com/swagger-ui.html#/)

## Links

### Backlog
Para o backlog optamos pelo Pivotal Tracker o mesmo pode ser acedido <a href="https://www.pivotaltracker.com/n/projects/2410465">aqui</a>.

### Documentos
Os documentos do projeto podem ser encontrados na pasta da Google Drive, contém sempre a versão mais atualizada dos mesmos uma vez que a ferramenta definida para a escrita de relatórios foi o Google Docs, a pasta encontra-se <a href="https://drive.google.com/drive/folders/1Q3gWHAxaBDn8KbCLEB_KCepWUc4GiT_G?usp=sharing">aqui</a>.

## Backend
Este repositório tem integração CI/CD. A parte de CI é tratada pelo Github Actions e a parte de CD é tratada pelo Heroku. 

A documentação da API foi feita utilizando o swagger que e pode ser acedida através do link: [documentação](https://iesapi.herokuapp.com/swagger-ui.html#/)
<img src="presentation/swagger.PNG">

#### Token de Acesso
Com exceção do Login e da documentação construída pelo Swagger, a totalidade dos caminhos da API são bloqueados e a informação é personalizada para cada utilizador. 
Para este efeito, se quisermos aceder a informação bloqueada temos passar o JSON Web Token no headers do pedido.
Um exemplo de código react seria:
```javascript
axios.get('https://iesapi.herokuapp.com/temperature/today', {headers: {"Authorization": `Bearer ${token}`}})
            .then(res => {
                var temp = [];
                temp = res.data;
                if (temp.length === 0) {
                    temp = []
                }
                let lista_labels = [];
                let lista_values = [];
                temp.forEach(valor => {
                    lista_labels.push(valor["time"].split(".")[0])
                    lista_values.push(valor["temp"])
                })
                this.setState({temp_labels: lista_labels, temp_values: lista_values});
            });
```
Por sua vez, se utilizarmos o postman deve ser feito usando OAuth2.0.
<img src="presentation/jwt.png">

Para obtermos o JWT temos de nos conectar ao endpoint `/login`. Um dos utilizadores disponíveis é:
* **Email** - `j.brito@ua.pt`
* **Password** - `test`

<img src="presentation/login.png">

### Arquitetura 
Foram construídos 5 módulos para a Repositório, Entidades, Serviços, Autenticação, Controlos, que se comunicam entre si para o funcionamento da API. 
Para além destes módulos, existe um para tratar da documentação e configuração e um para receber e armazenar as mensagens provenientes do RabbitMQ. 
<img src="presentation/architecture.PNG">


#### Comunicação dos módulos
A comunicação dos módulos pode ser representado de forma esquemática por:
<img src="presentation/modules.PNG">

### Caminhos da API 
A API pode ser dividida em 3 categorias. Autenticação, Recolha de Informações e Configuração.

* **Autenticação:** É responsável pela autenticação do utilizador e retornar um JWT Token com a informação da casa, email e nome do utilizador. O token sempre que existe pedidos com a API tem de ser passado de modo a garantir a integridade e segurança do sistema.

* **Recolha de Informações:** São os controllers de Humidade, Co2, Temperatura e os Acessos. Estes controllers apenas possuem métodos de leitura, isto é, apenas o método `GET` é permitido.

* **Configuração:** São os controllers de Políticas e as Credenciais `access/credential/`, nestes são permitidos os métodos padrões `PUT`, `POST` e `DELETE` e, obviamente, também é permitido o método `GET`
