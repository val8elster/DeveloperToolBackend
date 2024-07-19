# DeveloperToolBackend

## Remote Hosting

### Nur für für externes Hosting mit SSL: 

Für externes, öffentliches Hosting des Backends ist für die Datensicherheit SSL unabdingbar. In dem Beispielfall wird das Backend auf einem eigenen Server gehostet, welcher mit der Domain „elster.dev“ verknüpft ist.  
Für diese Domain existiert ein SSL-Zertifikat von Lets-Encrypt.  
Das Zertifikat muss für Springboot mittels Keytool in einen KeyStore umgewandelt werden.  
 
~~~
keytool -import -alias springboot -file  

myCertificate.crt -keystore keystore.p12 

-storepass 501ovWb&8
~~~

Anschließend müssen die im Bild zu sehenden Values in der application.properties gesetzt werden. 

![ssl](https://github.com/user-attachments/assets/fb33a7fd-8051-48c5-9d60-2dd6419b032b)

### Builden der .jar für vereinfachtes Starten des Backends 

Um das Backend mit allen dependencies ohne langwieriges Kopieren und Installieren der dependencies zu realisieren wird in diesem Schritt eine .jar Datei gebaut.  
Das Bauen funktioniert für Starten mit und ohne SSL identisch.  
Voraussetzungen:  

- OpenJDK 17
  ~~~
  sudo apt install openjdk-17-jdk openjdk-17-jre
  ~~~
- maven
  ~~~
  sudo apt install maven -y
  ~~~

![build1](https://github.com/user-attachments/assets/2424f0cc-8fb8-46e0-a03a-f287748c73ac)

Im gezeigten Fall wird das builden der .jar mit Hilfe von WSL Ubuntu 22.04 LTS durchgeführt.  

Im Root Verzeichnis des SourceCodes wird der Build einfach mittels  
~~~
mvn clean package
~~~
angestoßen.  

Die fertige .jar liegt im Unterordner target.   

Zum Starten in der Kommandozeile  
~~~
java -jar DeveloperToolBackend-0.0.1-SNAPSHOT.jar
~~~
eingeben nachdem entweder in den target Ordner gewechselt bzw. die Datei kopiert wurde.  

![build2](https://github.com/user-attachments/assets/a71e2ec3-ab4c-48fa-af80-99a98fea2ce4)

Für externes Hosting auf einem Remote Server muss der keystore.pk12 entsprechend dem Pfad in der application.properties abgelegt werden und empfiehlt sich das Starten des Backends mittels SCREEN, sodass nach schließen der Remote Sitzung das Backend weiterläuft. 

~~~
screen java -jar DeveloperToolBackend-0.0.1-SNAPSHOT.jar
~~~

Das Backend ist nun unter http(s)://localhost:8788 bzw. http(s)://Server-IP:8788 mittels REST erreichbar. 
