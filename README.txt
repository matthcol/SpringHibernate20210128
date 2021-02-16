Formation Java backend avec Spring-Hibernate
Fichiers sources de la formation: 
	https://github.com/matthcol/SpringHibernate20210128
Java Opensource : https://jdk.java.net/java-se-ri/11

Official documentations:
- Hibernate:
 * User Guide: https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html
    # Read chapter 2. Domain Model for mapping
    # Read chapter 15, 16, 17 for queries (HQL/JPQL, Criteria, Native queries)
- Spring Data
 * Jpa Repositories: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories
    # Read chapter 6.3 Query Methods
 * Mongo DB repositories: https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.repositories
	# Read chapter 15.3 Query Methods (16.3 fo react mode)
- Spring Testing: https://docs.spring.io/spring-framework/docs/current/reference/html/index.html
 * Testing RestController: https://spring.io/guides/gs/testing-web/
- Spring Security: https://docs.spring.io/spring-security/site/docs/5.4.2/reference/html5/
 

Commandes GIT :
- cloner un projet :
git clone https://github.com/matthcol/SpringHibernate20210128.git
- sycnhroniser les contributions extérieures
git pull
- où en suis-je
git status
- ajouter les fichiers à valider 
git add <monfichier ou repertoire>
- valider les changements
git commit -m "message significatif"

Commandes maven :
mvn compile
mvn test
mvn package

Framework Spring : spring.io
Spring Initializr : https://start.spring.io/
Annotations Spring
- classe principale @SpringBootApplication
- injection de dépendance : @Autowired
- controleur API Rest @RestController + @RequestMapping("/api/movies")
	* méthodes API : 
  	# méthode HTTP : @GetMapping, @PostMapping, 
    # retour objet à sérialiser en JSON : @ResponseBody
    # paramètres : 
    	° objet JSON à extraire du corps de requete : @RequestBody
- service : @Service
- mapping relationnel Hibernate: package javax.persistence (spec JEE JPA)
	* classe : @Entity
  * attribut/getter : par défaut tous sauvegardés
  	# id : @Id + @GeneratedValue
    # ne pa sauvegarder : @Transient
    # tuning sur la colonne : @Column
  * association :
    # type d'association : @ManyToOne, @OneToMany, @OneToOne, @ManyToMany
    	- by default eager : @ManyToOne, @OneToOne
      - by default lazy : @ManyToMany,  @OneToMany
    # tuning de la colonne de clé étrangère : @JoinColumn
- composant spring voulant utiliser un repository en mode transactionnel
	* @Transactional
Paramètrage application springboot
- Generation DDL
	spring.jpa.hibernate.ddl-auto=create-drop
	spring.jpa.hibernate.ddl-auto=update
	spring.jpa.hibernate.ddl-auto=none
- show SQL requests
	spring.jpa.show-sql=true
	spring.jpa.properties.hibernate.format_sql=true
	logging.level.org.hibernate.type.descriptor.sql=trace







