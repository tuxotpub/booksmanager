#Booksmanager 

Anwendungsaufbau: 
-----------------
Die Anwendung ist eine Bücherverwaltung mit CRUD Functionen, die aus folgenden Teilen besteht:
Author: besteht aus E-Mail-Adresse (Natural Id), Vor- und Nachname.
Book:, die einen Titel, Beschreibung, Autoren und eine ISBN-Nummer (Natural Id).
Magatine besteht aus Titel, Autoren, Erscheinungsdatum und eine ISBN-Nummer (Natural Id).  
Parchment: als basis Tabelle für Book und Magazine mit Inheritance Strategie JOINED.

Die angewendeten Tegnologien sind:
----------------------------------
*   Spring Boot 2
*   Spring Core/IoC als Application Context
*   Tomcat (Embedded schon in Boot)
*   Spring JPA/Hibernate
*   In Memory H2 Database
*   Lombok und MapStruct für Entities/Mapper 
*   Swagger für Rest Dokumentation 
*   Spring Unit/Integration Test Framework: JUnit, Mokito, Assertj, RestTemplate.
*   Maven für Dependencies Management und Java 8 SDK.
*   Intellij und spring initializr. 

Swagger Url:
------------
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Funktionen:   
-----------
Hier sind die folgenden Regeln vorgesehen:

*   Der DB Model sollte mit die 4 Tabellen Author, Parchemnt, Book, Magazine erfolgen. 
*   Realisiert, ist eine Implementierung der CRUD Funktionen/Dienste der beiden Tabellen. 
*   Die Beziehung zwischen diese Author und Parchment soll ManyToMany. Also ein Author kann mehrere Parchment haben, sowie eine Parchment kann von mehreren Authors geschrieben sein. 
*   Es darf keine Perchmant ohne Author bleiben. Also wenn alle zugehörigen Authors einer Parchment gelöscht sind, diese es soll automatisch (nach dem Löschen der letzten zugehörigen Author) gelöscht werden.
*   Im Gegenteil, es darf ein Auhor ohne zugewiesene Parchment bleiben.

Die Architektur diese Anwendung soll als Domain Driven Design (ähnliches) entworfen werden. 
Also mit den folgenden schichten: 

*   Entities/Model (Authors, Parchments...).
*   Repositories(DAO). Spring JPA repositories.
*   Services für DTOs.
*   Spring RestController.

Es ist nur einen Back-end Implementierung mit JSON Request/Response Media Type. Also ohne Front-End.
Für weitere Vorschläge bzw. Erweiterungen meiner Vorschlag, bin ich immer offen.

Die folgenden Punkte sollen ergänzt werden:
*   Die Test Fälle sollen geprüft/erweitert werden, da mehr logische Kombinationen gedeckt werden sollen.
*   Java Doc (bzw. in der Test Klassen) sollen ergänzt.
*   Die Log Deckung ist nicht ausreichend.
*   Aktivierung von 2L Cache Hibernate.
*   Metrics/ Measurement im Source Code einfügen um eine Optimierte Analyse zu schaffen.
*   Code Quality Reviewing ausführen.
*   Die Rest Services können auch erweitert werden.
*   Mehrere Profiles (Dev, Prod, Default) und relationale DB, bzw. MySql/PostgreSql eingestzt werden.
     
**Hinweis: Das ist einen Test anwendung und dient vor allen zum Evaluieren der Technologien. 
Deswegen die Anwendung hat keine bestimmte Spezifikation und nicht vollständig und wird die noch erweitert.**

