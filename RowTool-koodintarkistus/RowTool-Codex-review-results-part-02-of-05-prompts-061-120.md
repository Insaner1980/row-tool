# RowTool code review results, part 02 of 05, prompts 061–120

Tarkastus tehtiin nykyisestä lähdekoodista, asetuksista, testeistä ja KSP:n generoimasta Room-toteutuksesta. Lähtötilanteessa `main` vastasi `origin/main`ia ja työpuu oli puhdas. `PROJECT.md` luettiin toteutuskontekstiksi, mutta nykyinen koodi ja ajettavat testit käsiteltiin lähteenä. `PROJECT.md`:tä ei muutettu.

## Promptikohtaiset tulokset

| Prompt | Tarkastusalue | Tulos ja keskeinen todiste |
|---:|---|---|
| 061 | Route constants and navigation pattern agreement | Ei vahvistettua vikaa. `Screen.PROJECTS`, `SETTINGS` ja `COUNTER_PATTERN`, NavHostin kohteet, argumenttiavain ja kaikki tuotannon navigointikutsut vastaavat toisiaan. Ei muutosta. |
| 062 | Project ID URI encoding when building counter routes | Ei vahvistettua vikaa. Kaikki counter-reitit rakennetaan `Screen.counter`illa, joka tekee yhden `Uri.encode`-koodauksen; raakaa ID:tä ei liitetä muualla reittiin. Ei muutosta. |
| 063 | Counter argument decoding and lookup identity | Ei vahvistettua vikaa. Navigation Compose purkaa String-reittiargumentin ja sama arvo välitetään ViewModel-avaimeen, `CounterViewModel`iin ja Room-lookupiin ilman toista decodea. Ei muutosta. |
| 064 | Malformed or absent counter route argument handling | Ei vahvistettua vikaa. Reittimalli estää puuttuvan segmentin normaalissa navigoinnissa; nullable argumentti muunnetaan turvallisesti tyhjäksi ID:ksi, jonka Room-lookup päättää missing-fallbackiin. Ei muutosta. |
| 065 | Direct counter launch for a missing project | Ei vahvistettua vikaa. Ensimmäinen `observeProject`-emissio tuottaa `ReturnToProjects`-efektin ja `navigateToProjects` tyhjentää synteettisen start-counterin. Ei muutosta. |
| 066 | Direct counter launch for an archived project | Ei vahvistettua vikaa. Ensimmäinen arkistoitu Room-emissio ohjaa Projects-kohteeseen eikä aseta projektia viimeksi aktiiviseksi. Ei muutosta. |
| 067 | Stored last-active project validation against Room | Ei vahvistettua vikaa. `resolveLastActiveProjectId` hyväksyy tallennetun ID:n vain, jos `getById` löytää aktiivisen rivin. Ei muutosta. |
| 068 | Most-recently-updated active fallback ordering | Ei vahvistettua vikaa. Fallback käyttää `isArchived = 0`, `updatedAt DESC`, `id ASC`, joten tasatilanne on deterministinen. Ei muutosta. |
| 069 | Startup behavior when there are no active projects | Ei vahvistettua vikaa. Vain arkistoidut, tyhjä kanta ja stale preference johtavat `null`-starttiin ja Projects-kohteeseen. Ei muutosta. |
| 070 | Automatic navigation to a newly created project | Ei vahvistettua vikaa. `OpenProject` lähetetään vasta onnistuneen transaction-insertin jälkeen todellisella generoidulla ID:llä; virhehaarat lähettävät vain viestin. Ei muutosta. |
| 071 | Startup counter back-stack clearing | Ei vahvistettua vikaa. Valittu counter on graphin todellinen start destination; DirectCounterNavigationTest todentaa Projects-palun jälkeen, ettei counter jää Back-pinoon. Ei muutosta. |
| 072 | Counter-to-Projects back navigation | Ei vahvistettua vikaa. Sekä `BackHandler` että toolbarin Back käyttävät samaa `navigateToProjects`-polkua, joka poppaa Projects-entryyn tai rakentaa yhden puhtaan Projects-kohteen. Ei muutosta. |
| 073 | Archiving the currently displayed or last-active project | Ei vahvistettua vikaa. Arkistointi on transactionaalinen, matching preference tyhjennetään best-effort-polulla ja hyväksytty toiminto lähettää Projects-palun. Repository estää arkistoidun counterin mutaatiot. Ei muutosta. |
| 074 | Deleting the currently displayed or last-active project | Vahvistettu matalan vakavuuden kaksoisefektiriski. Poisto saattoi tuottaa Projects-palun sekä `delete()`-polusta että saman rivin Room-`null`-emissiosta. Korjaus kuvataan alempana. |
| 075 | Duplicate navigation effects, replay, and collector restart | Sama Promptissa 074 korjattu juurisyy koski tätä aluetta. Kaikki saman CounterViewModelin Projects-paluut kulkevat nyt yhden atomisen once-portin läpi; tavalliset Channel-efektit säilyvät ei-replayavana yhden kuluttajan virtana. |
| 076 | Route and Content responsibility boundary | Ei vahvistettua vikaa. Route-tiedostot omistavat lifecycle-keräyksen, dialogit, launcherit, side effectit ja navigointikäännökset; Content vastaanottaa state/action-arvot. Ei muutosta. |
| 077 | No repository or database resolution from rendering composables | Ei vahvistettua vikaa. Repositoryt ja `AppContainer` esiintyvät ViewModel-factory/Route-rajalla, eivät `*ScreenContent`-renderöinnissä. Ei muutosta. |
| 078 | Lifecycle-aware StateFlow collection in Routes | Ei vahvistettua vikaa. Kaikki tuotannon persistentit UI-StateFlowt kerätään `collectAsStateWithLifecycle`lla Route/MainActivity-tasolla. Ei muutosta. |
| 079 | Immutable UI state exposure and mutation containment | Ei vahvistettua vikaa. Mutable state -holderit ovat private-arvoja ja ulos annetaan `StateFlow`/`asStateFlow` sekä read-only `List`-tyypit. Ei muutosta. |
| 080 | StateFlow initial values and loading or empty-state distinction | Ei vahvistettua toiminnallista vikaa. Counter aloittaa `isLoading = true` -tilassa eikä näytä nollaa tai missing-fallbackia ennen Room-emissiota; startup-splash odottaa valinnan resoluutiota. Projects-listan tyhjä alkuarvo voi näkyä lyhyenä renderöintinä, mutta se ei käynnistä fallbackia tai tuhoavaa toimintoa, joten spekulatiivista loading-refaktorointia ei tehty. |
| 081 | `stateIn` and `shareIn` scope, sharing policy, and upstream lifetime | Ei vahvistettua vikaa. Screen-state käyttää ViewModel-scopea ja `WhileSubscribed(5_000)`-politiikkaa; startup-preferenssit ovat tarkoituksella eager. Päällekkäistä `shareIn`-ketjua ei ole. Ei muutosta. |
| 082 | Buffered Channel capacity and overflow behavior | Ei vahvistettua vikaa. Matalan volyymin navigation/message/haptic-efektit käyttävät `Channel.BUFFERED`ia ja `send`-kutsuja, joten täysi puskuri suspendaa eikä pudota tapahtumaa. Ei muutosta. |
| 083 | One-shot effect separation from persistent state | Ei vahvistettua vikaa. Navigointi, viestit, haptics ja import-complete ovat Channel-efektejä; persistentti StateFlow ei kanna kulutettavia eventtejä. Ei muutosta. |
| 084 | `rememberUpdatedState` around long-lived effect collectors | Ei vahvistettua vikaa. Projects-, Counter- ja Settings-collectorien ulkoiset callbackit sekä counterin haptics-asetus luetaan `rememberUpdatedState`n kautta. Ei muutosta. |
| 085 | LaunchedEffect, DisposableEffect, and SideEffect key correctness | Ei vahvistettua vikaa. Collectorien avain on ViewModel, keep-awake-effectin avaimet ovat view ja toteutuva boolean, ja system-bar-arvot päivitetään SideEffectissä. Ei muutosta. |
| 086 | ViewModelScope cancellation and operation lifetime | Ei vahvistettua vikaa. ViewModel-operaatiot käyttävät vain `viewModelScope`a; Route käyttää composition-scopea eikä irrallisia globaaleja scopeja löytynyt. Ei muutosta. |
| 087 | Dispatcher use for Room, DataStore, JSON, and file I/O | Ei vahvistettua vikaa. Room/DataStore suspend API:t hoitavat omat dispatcherinsa; backupin stream- ja JSON-työ sekä replacement on rajattu IO-dispatcherille. Ei muutosta. |
| 088 | Exception propagation and structured concurrency | Ei vahvistettua vikaa. Tunnetut IO/SQL/security-virheet muunnetaan oikeissa ViewModel/repository-rajoissa efekteiksi tai structured resultiksi; cancellationia ei niellä. Ei muutosta. |
| 089 | Rapid repeated UI actions and reentrancy control | Vahvistettu matalan vakavuuden importin kaksoiskäynnistys. Vahvistusdialogi pysyi näkyvissä replacementin ajan ja jokainen painallus käynnisti uuden operaation. Korjaus kuvataan alempana. Muut nimetyt dialogitoiminnot dismissataan ennen käynnistystä, ja counter-mutaatiot serialisoidaan repository-Mutexilla sekä rajanopeilla no-op-tuloksilla. |
| 090 | Action bundle identity, freshness, and stale lambda capture | Ei vahvistettua vikaa. Action-bundlet rakennetaan nykyisestä kompositiosta, project-ID:t sidotaan nykyiseen stateen ja pitkäikäiset collector-callbackit päivitetään erikseen. Ei muutosta. |
| 091 | Project ID generation and persisted identity stability | Ei vahvistettua vikaa. Tuotanto käyttää UUID:tä; ID säilyy copy/mapping/edit/archive/mutation/export/import-polkujen läpi ja import hylkää blank/duplicate-ID:t. Ei muutosta. |
| 092 | Project name trimming at every authoritative write path | Ei vahvistettua vikaa. `ProjectValidation` trimmaa create/update-arvot ja BackupCodec käyttää samaa validointia ennen hyväksyntää. UI-validointi ei korvaa repository-validointia. Ei muutosta. |
| 093 | Blank project name rejection | Ei vahvistettua tuotantovikaa. Editorin save estyy blankille nimelle, authoritative `ProjectValidation` hylkää sen ja import palauttaa structured invalid-resultin. Repositoryn suora virheellinen create/update on ohjelmointivirhe ja päättyy eksplisiittiseen `IllegalArgumentException`iin, eikä sellaista UI-kutsupolkua löytynyt. Ei muutosta. |
| 094 | Maximum 60 Unicode code point name limit | Ei vahvistettua vikaa. Sekä editori että domain-validointi käyttävät `codePointCount`ia; emoji-rajojen testit kattavat 60 ja 61 code pointia. Ei muutosta. |
| 095 | Persisted counter-unit decoding fallback for unknown database values | Ei vahvistettua vikaa. Entity mapper käyttää `CounterUnit.fromPersisted`, joka fallbackaa `ROWS`iin ilman tietokantakirjoitusta. Ei muutosta. |
| 096 | Strict rejection of unknown counter units in imported backups | Ei vahvistettua vikaa. BackupCodec vaatii täsmällisen enum-nimen ja palauttaa `INVALID_PROJECT`; fallbackia ei käytetä importissa. Ei muutosta. |
| 097 | Counter count lower bound of zero | Ei vahvistettua vikaa. Domain/import-validointi, decrementin `coerceAtLeast(0)`, manual-set-raja, resetin validoitu start ja transactionaalinen undo estävät negatiivisen tuotantoarvon. Ei muutosta. |
| 098 | Counter count upper bound of 999,999 | Ei vahvistettua vikaa. Domain/import/manual-set-validointi ja incrementin `coerceAtMost(MAX_COUNT)` kattavat kaikki tuotannon write-polut; no-op ei lisää historiaa. Ei muutosta. |
| 099 | Start value restricted to zero or one | Ei vahvistettua vikaa. Yhteinen authoritative validointi hyväksyy vain 0/1, reset käyttää tallennettua validoitua arvoa ja mapperit säilyttävät sen. Ei muutosta. |
| 100 | Optional target count presence and range | Ei vahvistettua vikaa. Disabled/blank editoriarvo mapataan nulliksi ja sekä editori että domain/import hyväksyvät vain 1–999999. Ei muutosta. |
| 101 | Optional repeat length presence and range | Ei vahvistettua vikaa. Disabled/blank arvo on null ja yhteinen raja on 2–999; derived display ei muuta persisted arvoa. Ei muutosta. |
| 102 | Cross-field count, start, target, and repeat consistency | Ei vahvistettua vikaa. Arvot validoidaan tarkoituksella itsenäisinä; esimerkiksi count saa ylittää targetin, eikä toteutukseen lisätty keksittyä cross-field-sääntöä. Ei muutosta. |
| 103 | Archived project immutability invariant | Ei vahvistettua vikaa. Counterin mutate/undo tarkistaa `isArchived` transactionissa myös stale UI -kutsulle. Projects-ruudun arkistoidun projektin metadatan editointi on nykyisen UI:n sallima toiminto, joten sitä ei virheellisesti estetty. Ei muutosta. |
| 104 | Timestamp validity and update semantics | Ei vahvistettua vikaa. Create käyttää yhtä millisekuntikelloa molempiin arvoihin; edit/archive/restore/mutation/undo säilyttävät `createdAt`in ja päivittävät `updatedAt`in vain onnistuneessa muutoksessa. Boundary no-op ei päivitä. Import säilyttää sopimuksen Long-epoch-arvot eikä sekunti/millis-muunnosta tehdä. Ei muutosta. |
| 105 | Structured repository result and error semantics | Ei vahvistettua vikaa. Counter-mutaatio erottaa Changed, NoOp, Missing, Archived ja Invalid; persistence failure etenee SQLException-rajalle. Backup replacement erottaa Success/Failure ja rollback-testit kattavat kirjoitusvirheen. Ei muutosta. |
| 106 | Database filename and schema version identity | Ei vahvistettua vikaa. Tuotanto käyttää vain `RowToolDatabase.create`, nimeä `rowtool.db` ja `@Database(version = 1)`; repositorytestit käyttävät eristettyä in-memory-kantaa. Ei muutosta. |
| 107 | Projects entity table name, columns, and nullability | Ei vahvistettua vikaa. Entity, KSP SQL ja committed schema sisältävät kaikki kymmenen projektikenttää; vain target/repeat ovat nullable. Ei muutosta. |
| 108 | Project primary key and identity conflict behavior | Ei vahvistettua vikaa. `id` on non-null TEXT primary key ja create/import käyttävät `OnConflictStrategy.ABORT`ia, eivät silent replacea. Ei muutosta. |
| 109 | Projects active/archive ordering index | Ei vahvistettua vikaa. Entity, KSP ja schema sisältävät indeksin `(isArchived, updatedAt)` samassa järjestyksessä kuin ryhmittely-/ordering-kysely. Ei muutosta. |
| 110 | Counter-history entity columns and change-reason storage | Ei vahvistettua vikaa. Entity/KSP/schema sisältävät ID:n, projectId:n, previous/new countit, reason-stringin ja createdAtin oikeilla non-null-tyypeillä. Ei muutosta. |
| 111 | History foreign key and cascade deletion | Ei vahvistettua vikaa. FK on `counter_history.projectId -> projects.id ON DELETE CASCADE`; generoitu `onOpen` suorittaa `PRAGMA foreign_keys = ON`, ja cascade-testi on olemassa. Ei muutosta. |
| 112 | History projectId and ID index for newest lookup | Ei vahvistettua vikaa. `(projectId, id)`-indeksi tukee `ORDER BY id DESC LIMIT 1`-lookupia ja saman projektin retention-alikyselyä. Ei muutosta. |
| 113 | Committed Room schema JSON consistency and reproducibility | Ei vahvistettua vikaa. Debug-KSP generoi saman taulu-/index-/FK-rakenteen ja identity hashin `929fe220bd5bf1864da2e8bda3cdeb39`; schemafileen ei syntynyt diffiä. Ei muutosta. |
| 114 | Counter-unit and change-reason storage conversion | Ei vahvistettua vikaa. Tuotanto kirjoittaa vakaat enum-`name`-stringit; Room counter-unit fallbackaa, import on strict ja tuntematon vanha history reason fallbackaa turvallisesti manual-setiksi vain undo-tuloksen luokituksessa. Ei muutosta. |
| 115 | Domain-to-entity and entity-to-domain round-trip fidelity | Ei vahvistettua vikaa. Mapperit siirtävät kaikki kymmenen kenttää molempiin suuntiin; ainoa dokumentoitu fallback on Roomista luetun counter-unitin `ROWS`. Ei muutosta. |
| 116 | Active-project DAO query filtering and ordering | Ei vahvistettua vikaa. Reaktiivinen `observeAll` järjestää active-ryhmän ensin `updatedAt DESC, id ASC`, ja ViewModel suodattaa aktiiviset säilyttäen järjestyksen. Ei muutosta. |
| 117 | Archived-project DAO query filtering and ordering | Ei vahvistettua vikaa. Sama Room Flow emittoi restore/delete-muutokset; archived-suodatus säilyttää `updatedAt DESC, id ASC` -järjestyksen erillään aktiivisista. Ei muutosta. |
| 118 | Single-project lookup and active-only lookup semantics | Ei vahvistettua vikaa. Counter/edit/archive tarvitsevat any-project `getById`/`observeById` -semantiikan; startup tarkistaa aktiivisuuden ja käyttää erillistä active-only fallback-kyselyä. Ei muutosta. |
| 119 | DAO insert, update, and conflict strategy correctness | Ei vahvistettua vikaa. Insertit ABORTtaavat konfliktin, update/delete tapahtuvat ID-kohdistetusti transaction/Mutex-rajoissa ja import poistaa vanhat rivit sekä insertoi validoidun joukon yhdessä transactionissa. Ei muutosta. |
| 120 | Database transactions, callbacks, and migration boundary | Ei vahvistettua vikaa. Create/edit/archive/delete/mutate/undo ja replacementin monivaiheiset DB-operaatiot ovat Room-transactioneissa; callbacks eivät muuta käyttäjädataa, eikä version 1 builderissa ole destructive fallbackia tai kuvitteellista migrationia. Ei muutosta. |

## Tehdyt korjaukset

### Promptit 074–075: yksi Projects-paluuefekti per counter-kohde

- Muutettu `app/src/main/java/com/finnvek/rowtool/ui/screens/counter/CounterViewModel.kt`.
- Juurisyy: poiston oma onnistumispolku ja saman poistamisen Room-`null`-emissio olivat kaksi itsenäistä `ReturnToProjects`-tuottajaa.
- Korjaus: kaikki CounterViewModelin Projects-paluut kulkevat `returnToProjects`-metodin atomisen `compareAndSet(false, true)` -portin kautta. Ensimmäinen fallback säilyttää nykyisen viestin ja välittömän navigoinnin; myöhemmät duplikaatit sivuutetaan.
- Lisätty `app/src/test/java/com/finnvek/rowtool/ui/screens/counter/CounterViewModelTest.kt`, joka käynnistää todellisen in-memory Room -poiston ja varmistaa yhden paluuefektin.

### Prompt 089: yksi import replacement kerrallaan

- Muutettu `app/src/main/java/com/finnvek/rowtool/ui/screens/settings/SettingsViewModel.kt`.
- Juurisyy: importin confirmation dialog pysyi näkyvissä operaation ajan, eikä `confirmImport`issa ollut in-flight-porttia.
- Korjaus: atominen `importInProgress` hyväksyy vain ensimmäisen vahvistuksen, ja `finally` vapauttaa portin sekä onnistumisen, failure-resultin että cancellation/exception-polun jälkeen. Nykyinen retry-käytös failure-resultin jälkeen säilyy.

## Validointi

- `CounterViewModelTest` sekä main/test ktlint: lopullinen kohdistettu ajo onnistui (`BUILD SUCCESSFUL`).
- `BackupAndPreferencesRepositoryTest` sekä main/test ktlint: onnistui (`BUILD SUCCESSFUL`); importin transaction-, rollback-, preference-failure- ja export-polut säilyivät.
- KSP-debug-toteutus generoitiin testiajoissa. `RowToolDatabase_Impl`in versio, SQL, FK:n aktivointi ja identity hash vastaavat committed schemaa; schemafileen ei syntynyt diffiä.
- Kaksi testikehityksen väliajoa epäonnistui ennen lopullista vihreää testiä, koska uusi host-testi odotti Roomin invalidointia väärällä test scheduler -rajalla. Counter-testi synkronoitiin authoritative state -emissioon. Importille kokeiltu vastaava host-efektitesti poistettiin, koska Roomin oma executor teki mittauksesta epävakaan; epävakaata testiä ei jätetty lähteisiin.
- Lopullinen koko projektin validointi onnistui komennolla `./gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug :app:kspDebugKotlin :app:assembleDebugAndroidTest :app:ktlintCheck :app:detekt --no-daemon --no-configuration-cache --console=plain` (`BUILD SUCCESSFUL`, 100 tehtävää: 15 suoritettu, 85 ajan tasalla).
- Unit-testitulokset: 75 testiä, 0 failurea, 0 erroria ja 0 skipped-testiä yhdeksässä testisuitessa.
- Android Lint valmistui ilman virheitä. Viisi varoitusta olivat vain saatavilla olevien AGP-, Navigation-, Compose rules- ja Sonar-versioiden ilmoituksia; riippuvuuspäivitykset eivät kuuluneet tähän tarkistukseen.
- Detekt SARIF sisälsi 0 tulosta. `git diff --check` onnistui; Git ilmoitti vain työpuun LF/CRLF-normalisointivaroituksista.

## Varmistamatta jäävä runtime-raja

Fyysistä tai emuloitua Android-laitetta ei käytetty tämän osan aikana. Siksi navigation back stackin, Activity recreationin ja UI:n nopean monipainalluksen varsinainen device-instrumentointi jää laiteportiksi. Lähdepolut, host-testit, Room/KSP-rakenne ja käännös validoivat tehdyt repository/ViewModel-muutokset paikallisesti.
