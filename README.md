Prosjektet er en enkel handleliste-applikasjon bygget med Spring Boot, MySQL, og en frontend med HTML, CSS (Bootstrap) og jQuery. Brukere kan legge til, oppdatere og slette varer, som lagres i en database.

Backend består av et REST API med fire endepunkter for å håndtere varer, og bruker JdbcTemplate for databaseoperasjoner. Inputvalidering sikrer at kun gyldige varer lagres, og logging håndterer eventuelle feil.

Frontend har et brukervennlig grensesnitt der varer legges til, vises og oppdateres dynamisk. Designet er responsivt med en moderne stil og semi-transparent bakgrunn. 

Jeg har hentet inspirasjon fra et frontend-prosjekt som lager en to-do liste;
https://drive.google.com/file/d/1tV4TubuTzfU3-Kld1HLTcSPSmD37s0WV/view?usp=drive_link