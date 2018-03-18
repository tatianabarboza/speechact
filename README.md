# speechact
Finding Speech Act in Events Logs
Busca de atos de fala em log de eventos

Propose:
Using Natural Language Process - Stanford Java, Speech Act Theory and Linguistic Perspective, it was possible extract kinds of speech act are considered making decisions. This application was build using a dataset of a company which provides services of infraestructure. This dataset has exchange mensages among clients and employees.

Metodology:
1) Cleaning informations
It was done a cleaning of every informations aren't considered importants to miner points of decision and mensages that happing before of these points, as telfephone numbers, emails, address, especial characters, etc;.

2) Mining Making Decision 
Through of tecniques investigation for discovering proposes business rule in paper Campos et al. (2017) and group of verbs referring a making decision proposed at Gunnarsson, M. (2006) , was code built for extrating all points of making decision among mensages (
already mentioned before), using functions and libs of Stanford NLP [Reese, 2005].


3) Mining Speech Acts Featuring Decisions  
After to find making decisions, it was listed all mensages which precede these decisions. In mensages, it had more than one kind of speech act. For having more precision in results, it was find the main speech act of each mensage, using rules avaliable  in Speech Act Theory. Thus, it was possible to identify the most found speech act wich can direct a making decision.   


Plataform:
Java 1.8
Maven
Netbeans IDE  8.2
 

Procedure for application:
1) Cloning application for PC c:\ (Windows), \home (Linux), etc.;
2) This application was built of Maven;
3) Verify if the file paths is according to where you cloned this application; 
4) There is a file with examples of events log. (speechact/arquivos/LogMessage.csv);
5) The main file is AtosDeFalaDecisao.java 
 
References:

[1] Campos, J., Richetti,P., Baião, F.A., Santoro, F.M.:"Discovering business rules in Knowledge Intensive Process through decision mining experimental study." In: 5th International Workshop on Declarative/Decision/Hybrid Mining & Modeling for Business Processes (DeHMiMoP’17). [2017]

[2] Bach,K., Harnish, R.M.: Linguistic Communication and speech acts. In: The MIT Press – Cambridge (2007)

[3] Gunnarsson,M: Group Decision-Making – Language and Interaction. In: Therése Foleby -ISBN: 91-975752-6-7 (2006)

[4] Reese, R.M.: Natural Language Processing with Java. In: Packt Publishing . Disponível em: www.it-ebook.info. . Acessado em 10/09/2017.

