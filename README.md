# PIISW, WIT, IO, 2021/2022, semestr letni

# Lista zadań nr 4
## Wprowadzenie

Posiadamy system monitorowania zdarzeń na serwerach. Informacje o zdarzeniach muszą być zapisywane w relacyjnej bazie danych.

W celu realizacji zadania należy zapoznać się z dokumentacją:

- Spring data JPA
- JPQL reference

1. Utwórz nowe prywatne repozytorium GitHub oraz zaimportuj do niego zawartość repozytorium https://github.com/pwr-piisw/lista4-starter. 
   Projekt zawarty w repozytorium został zbudowany w oparciu o Spring Boot i wykorzystuje JPA oraz bazę danych H2.
2. Zapoznaj się ze strukturą klas zaprezentowanych na diagramie z rysunku 2. Klasy zdarzeń (relacja dziedziczenia) zostały zmapowane z wykorzystaniem metody `TABLE_PER_CLASS`.
3. Zapoznaj się z mapowaniem relacji `Event` _→_ `Server`.
4. Baza danych tworzona jest w momencie startu aplikacji/testów automatycznie z pliku `schema.sql`
5. Zapoznaj się ze skryptem `data-h2.sql` – skrypt ten jest uruchamiany przy starcie testów i ma za zadanie załadowanie danych testowych do bazy.
6. Zapoznaj się klasami:

   (a) `ServerService`– implementacja usług dotyczących klasy `Server` \
   (b) `ServerRepository`– interfejs DAO dostępu do danych klasy Server. Zauważ, że interfejs ten nie posiada implementacji. 
   Biblioteka spring-data automatycznie generuje kod odpowiedzialny za komunikację z bazą danych. \
   (c) `ServerServiceTest`– demonstruje działanie klas

7. Każde zadanie musi posiadać implementację w teście o nazwie `TaskX` gdzie `X` to numer zadania. Dla każdego zadania został przygotowany szablon testu.

## Zadania

1. (3 pkt) Zmodyfikuj klasę Server, dodaj _Optimistic Locking_ oraz roszerz klasę o dwa pole:

   (a) `createdDate`- ustawianą w momencie tworzenia obiektu klasy \
   (b) `lastUpdateDate`- aktualizowaną przy każdym zapisie obiektu klasy.

   Następnie zapewnij, że dane przy usuwaniu nie będą usuwane fizycznie z bazy danych, ale pozostaną w niej z odpowiednio ustawioną flagą `isActive` tzw.: soft delete. 
   Zapewnij, że usunięte dane nie będą możliwe do znalezienia w trakcie wywoływania innych metod do wyszukiwania i aktualizowania danych.

   **Wskazówka**: wykorzystaj istniejące już adnotacje. Pamiętaj o wymaganych migracjach w modelu bazodanowym.

2. (3 pkt) Utwórz interfejs `EventRepository` rozszerzający `org.springframework.data.jpa.repository.JpaRepository`. Zadeklaruj metodę, która dla zadanych parametrów:
   - `LocalDateTime start`,
   - `LocalDateTime end`,
   - `boolean toBeAnalyzed`

   zwróci wszystkie zdarzenia `Event`, które posiadają czas zarejestrowania taki, że:

   - start _<_ Event.time _<_ end
   - oraz flagaEvent.analysisRequired=toBeAnalyzed.

   Zadeklaruj ją jako metodę umożliwiającą pobieranie danych z wykorzystaniem stronicowania.

   **Wskazówka**: wystarczy nazwać metodę zgodnie z konwencją `findBy`, `Between`, `And`, wykorzystaj `Page` oraz `Pageable`.

3. (3 pkt) Bulk operacje są operacjami, w których zamiast pobierać encje z bazy danych, a następnie je modyfikować i zapisać bądź usuwać, 
   wykonuje się zapytanie modyfikujące ich stan bezposrednio na bazie danych.
   - _Bulk delete_. Zadeklaruj wEventRepositorymetodę usuwającą wszystkie zdarzenia `Event`, gdzie Event.time _<_ X. Zadeklaruj X jako _named parameter_.
   - _Bulk update_. Zadeklaruj wEventRepositorymetodę modyfikującą wszystkie zdarzenia Event określonej podklasy w ten sposób, że atrybut toBeAnalyzed przyjmie
   wartość’T’, dla wszystkich zdarzeń spełniających warunek:Event.duration _>_ X.
   Metoda powinna przyjmować następujące argumenty:
   **-** Class<? extends Event> clazz
   **-** int minDuration

   **Wskazówka**: konieczne jest wykorzystanie adnotacji `@Modifying`, `@Query`, `@Param` (z pakietu `org.springframework.data`)

4. (2 pkt) Zadeklaruj w `EventRepository` metodę zwracającą listę obiektów typu `ServerStatistic` — ile zdarzeń zostało zarejestrowanych na poszczególnych serwerach.

   **Wskazówka**: konieczne jest wykorzystanie anotacji @Query (z pakietu `org.springframework.data`), tzw. JPQL Constructor Expressions oraz klauzuli group by.

5. (3 pkt) Zmodyfikuj testy w taki sposób, by zwracały mocki zamiast rzeczywistego obiektu. Wykorzystaj w tym celu `ServerService.findByNameorazServerRepository`.

   **Wskazówka**: zadeklaruj w teście `ServerRepository` jako `@MockBean`, skorzystaj np.: z `Mockito.when`, `Mockito.eq`, `Mockito.thenReturn`.

6. (4 pkt) Rozszerz model danych o nowe klasy _Comment_ i _Follower_. Zgodnie z podanym niżej diagramem
   Przygotuj metodę, która pozwoli dla danego _Follower_ identyfikowanego po _userId_ wyświetlić _Event.description_ , _Event.time_ , _Event.analysisRequired_ , _Comment.content_ oraz _Follower.subscriptionDate_. 
   Opisane zapytanie moze zwrócić wiele rekordów, dlatego zapewnij, że tylko wymagane dane zostaną załadowane i odbędzie się to w jak najmniejszej liczbie zapytań (najlepiej jednym zapytaniu do bazy danych).

   **Wskazówka**: zapoznaj się z działaniem adnotacji `@NamedEntityGraph`.
