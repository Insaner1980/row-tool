# RowTool-koodintarkistuksen tulokset, osa 05/05, promptit 241–301

Tämä raportti perustuu 3.9.2026 tarkastettuun nykyiseen työpuuhun. Tarkastus aloitettiin likaisesta työpuusta: 18 seurattua tiedostoa oli muokattu ja viisi tiedostoa tai hakemistoa oli seuraamatta. Ne käsiteltiin käyttäjän ennestään olemassa olevana työnä, eikä niitä palautettu, ylikirjoitettu, commitoitu tai julkaistu.

PROJECT.md luettiin tarkastuksen alussa ja uudelleen lopuksi. Se vastasi tarkastettua toteutusta eikä sitä muutettu.

## Promptikohtaiset tulokset

| Prompti | Tulos | Tarkastettu näyttö ja toimenpide |
|---:|---|---|
| 241 | Ei vahvistettua vikaa | Haptics-asetus kulkee PreferencesRepositoryn Flowsta CounterRouteen. Pitkäikäinen efektikeräin lukee uusimman arvon rememberUpdatedState-rakenteen kautta, joten pois kytketty asetus estää kaikki CounterEffect.Haptic-kutsut muuttamatta laskuritoimintoa. Ei muutosta. |
| 242 | Ei vahvistettua vikaa | CounterViewModel tuottaa kevyen haptisen efektin vain hyväksytylle tavalliselle lisäykselle, vähennykselle ja undo-toiminnolle. Manuaalinen asetus ja reset eivät tuota tavallista napautepalautetta. Ei muutosta. |
| 243 | Ei vahvistettua vikaa | Puuttuva, arkistoitu tai raja-arvossa muuttumaton projekti palauttaa rakenteisen no-op- tai virhetuloksen eikä Haptic-efektiä. Hylätty muutos ei lisää historiaa. Ei muutosta. |
| 244 | Ei vahvistettua vikaa | Hyväksytyn lisäyksen repeat-rajan tunnistus vaatii positiivisen uuden arvon ja repeatLength-jaollisuuden. Se valitsee vahvan palautteen. Ei muutosta. |
| 245 | Ei vahvistettua vikaa | Target-palaute syntyy vain, kun edellinen arvo oli tavoitetta pienempi ja uusi arvo osuu täsmälleen tavoitteeseen. Ei muutosta. |
| 246 | Ei vahvistettua vikaa | Repeat- ja target-ehdot yhdistetään yhdeksi boolean-päätökseksi ja yksi CounterEffect.Haptic sisältää vain strong-lipun. Samalla napautuksella ei lähetetä kahta palautetta. Ei muutosta. |
| 247 | Ei vahvistettua vikaa | Kevyt palaute käyttää CLOCK_TICK-vakiota. Vahva palaute käyttää API 30+:ssa CONFIRM-vakiota ja minSdk 29:ssä LONG_PRESS-varavaihtoehtoa. View.performHapticFeedback ei edellytä manifestilupaa. Ei muutosta. |
| 248 | Ei vahvistettua vikaa | Teemavaihtoehdot ovat yhden valinnan selectable-rivejä, valittu arvo tulee DataStoresta ja tallennus kulkee ViewModelin kautta. System-, light- ja dark-vaihtoehdot on lokalisoitu. Ei muutosta. |
| 249 | Ei vahvistettua vikaa | Haptics- ja keep-awake-rivit ovat kokonaisina toggleable-kohteita. Niiden Switch-komponentit eivät käsittele erillistä onCheckedChange-kutsua, joten yksi aktivointi tuottaa yhden tallennuksen. Ei muutosta. |
| 250 | Ei vahvistettua vikaa | Keep-awake-asetus on selitetty lokalisoidulla tekstillä. CounterRoute asettaa keepScreenOn-lipun vain asetuksen ollessa päällä ja aktiivisen, arkistoimattoman projektin ollessa auki; DisposableEffect poistaa lipun poistuttaessa. Ei muutosta. |
| 251 | Ei vahvistettua vikaa | CreateDocument- ja OpenDocument-launcherit omistaa SettingsRoute. ViewModel ei säilytä Activitya, launcheria, NavControlleria tai ContentResolveria, vaan saa resolverin ja URI:n yksittäiseen operaatioon. Ei muutosta. |
| 252 | Ei vahvistettua vikaa | Tuonnin esikatselu näyttää aktiivisten ja arkistoitujen projektien määrät sekä korvaamisen ja undo-historian menettämisen vaikutuksen. Replace- ja Cancel-toiminnot ovat erilliset ja lokalisoidut. Ei muutosta. |
| 253 | Ei vahvistettua vikaa | Tuontipyynnöt numeroidaan, vanhentunut tulos ohitetaan ja vahvistus on suojattu AtomicBooleanilla. Epäonnistuminen jättää esikatselun korjattavaksi tai peruttavaksi; onnistuminen tyhjentää sen. Erillistä etenemisindikaattoria ei ole, mutta vaarallista rinnakkaiskorvausta tai todistettua toimintavirhettä ei löytynyt. Ei muutosta. |
| 254 | Ei vahvistettua vikaa | Versio luetaan sovelluksen omasta PackageInfosta. RowTool- ja Finnvek-identiteetti vastaavat pakettia, PROJECT.md:tä ja lokalisoituja tekstejä. Ei muutosta. |
| 255 | Ei vahvistettua vikaa | Asetusnäkymän tietosuoja- ja liiketoimintamallitekstit vastaavat toteutusta: paikallinen data, manuaalinen SAF-vienti, maksullinen lataus, ei mainoksia, tilejä, analytiikkaa, tilausta tai sovelluksen sisäistä laskutusta. Ei muutosta. |
| 256 | Ei vahvistettua vikaa | Settings-, Projects- ja Counter-sisältö käyttää yhteisiä 20 dp sivumarginaaleja ja 600 dp enimmäisleveyttä. Puhelin- ja leveä tila eivät venytä sisältöä hallitsemattomasti. Ei muutosta. |
| 257 | Ei vahvistettua vikaa | ProjectEditorDialog on rajattu 560 dp leveyteen ja 88 prosenttiin ikkunakorkeudesta, sisältö vierii ja käyttää imePaddingia. CountEditorDialogin syöttö ja virhetila mahtuvat vastaavaan dialogirakenteeseen. Ei muutosta. |
| 258 | Ei vahvistettua vikaa | Suuren laskurin fonttikoko pienenee numeromäärän mukaan ja skaalautuu käytettävissä olevaan leveyteen. Arvo formatoidaan nykyisellä Locale-asetuksella. Ei muutosta. |
| 259 | Ei vahvistettua vikaa | Fonttiskaalan ylittäessä 1,3 laskurin oma skaala kompensoi kasvua, mutta säilyttää järjestelmän tekstiskaalauksen. Muu teksti käyttää Material-typografiaa. Ei muutosta. |
| 260 | Ei vahvistettua vikaa | Counter-sisältö on pystysuunnassa vieritettävä, joten lyhyt tai vaakasuuntainen ikkuna ei leikkaa ydintoimintoja. Dialogien korkeus on rajattu ja sisältö vierii. Ei muutosta. |
| 261 | Ei vahvistettua vikaa | 600 dp sisältöraja keskittää näkymät tabletilla. Laskurin ohjainryhmä ja projektikortit eivät veny koko leveydelle. Ei muutosta. |
| 262 | Ei vahvistettua vikaa | Scaffold- ja safeDrawing-insetit suojaavat järjestelmäpalkkien alueet. Yhteinen snackbar huomioi vaaka- ja alainsetit sekä Projects-FABin lisätilan. Ei muutosta. |
| 263 | Ei vahvistettua vikaa | Laskurin kuvapainikkeilla on vähintään 48 dp kosketusalue; nykyinen ohjainkoko tuottaa tätä suuremman kohteen. IconButton-, valikko- ja toggle-komponentit käyttävät Materialin kosketusmittoja. Ei muutosta. |
| 264 | Ei vahvistettua vikaa | Laskurin ohjaimet määrittävät button-roolin, lokalisoidut sisältökuvaukset ja disabled-semanticsin. Toggle- ja valintarivit välittävät kytkin- tai valintatilan koko riville. Ei muutosta. |
| 265 | Ei vahvistettua vikaa | Dialogikentät ovat normaalissa Compose-fokusjärjestyksessä ja CountEditorDialog pyytää fokuksen avautuessaan. Suljetun arkisto-osion sisältö poistuu kompositiosta eikä jää fokuspolkuun. Ei muutosta. |
| 266 | Ei vahvistettua vikaa | Tavoite, repeat-tila, aktiivisuus, virheet ja disabled-tila ilmaistaan tekstillä tai semantiikalla, eivät yksin värillä. Kenttävirheillä on tukiteksti. Ei muutosta. |
| 267 | Ei vahvistettua vikaa | Juuren ja drawable-nodpi-hakemiston plus-, minus- ja undo-WebP-tiedostot ovat 500 × 500 pikseliä ja binäärisesti identtiset. SHA-256: plus CAFB6292CD6A3F25E43F9953406EA6646A8274F34E1FAF14A470D09B07A42AC5, minus 3CBE07BD32A0ADD4F0A2B31DA8635563B01449B90E2C21B662E6B8FBE5A597B6, undo B59814D6FFFC0BD5B43BB936D2D2D3EAA79EA71E1B53B76181A36D1E76464075. Ei muutosta. |
| 268 | Ei vahvistettua vikaa | CounterImageButton käyttää ContentScale.Fit-asetusta ilman tinttiä tai ColorFilteriä. Painallusanimaatio muuttaa vain kuvan skaalaa vakion kokoisen ulomman kosketusalueen sisällä. Ei muutosta. |
| 269 | Ei vahvistettua vikaa | locales_config.xml ja resurssihakemistot sisältävät saman joukon: oletus, fi, sv, de, fr, es, pt, it, nb, da ja nl. Jokaisessa values-hakemistossa on 107 samaa resurssiavainta. Ei muutosta. |
| 270 | Ei vahvistettua vikaa | Merkkijonojen placeholder-nimet, tyypit ja plural-itemien placeholderit täsmäävät kielten välillä. Monikkomuotojen kielikohtainen määrä saa erota CLDR-sääntöjen mukaisesti. Laskuri käyttää locale-muotoilua. Ei muutosta. |
| 271 | Ei vahvistettua vikaa | Vaalea ja tumma ColorScheme käyttävät teemaan nimettyjä semanttisia pareja. ThemeTest tarkistaa keskeiset kontrastit eikä tarkastuksessa löytynyt lukukelvotonta väriparia. Ei muutosta. |
| 272 | Ei vahvistettua vikaa | Teema on tarkoituksellisesti kiinteä lämmin vaalea/tumma Material 3 -teema. Lähteessä ei ole dynamicLightColorScheme- tai dynamicDarkColorScheme-kutsuja, joten järjestelmän dynaaminen väri ei muuta ilmettä. Ei muutosta. |
| 273 | Ei vahvistettua vikaa | Tuotanto käyttää keskitettyä Material-typografiaa ja FontFamily.SansSerif-varaperhettä. Outfit-fonttitiedostoa ei ole eikä PROJECT.md väitä sitä käytettävän. Ei muutosta. |
| 274 | Ei vahvistettua vikaa | Toistuvat sisältöleveydet, sivumarginaalit, dialogimitat ja ohjainmitat on keskitetty RowToolDimensiin ja yhteisiin komponentteihin. Yksittäisille näkymille kuuluvia arvoja ei tarvitse nostaa globaaliksi. Ei muutosta. |
| 275 | Ei vahvistettua vikaa | Route-taso kerää StateFlowt lifecycle-aware-menetelmällä ja välittää pienet tilat sekä vakaat action-bundlet Content-tasolle. Tarkastuksessa ei löytynyt laskennallisesti raskasta uudelleenkompositiotyötä. Ei muutosta. |
| 276 | Ei vahvistettua vikaa | Aktiivinen LazyColumn käyttää projektin id:tä avaimena. Arkistokortit saavat saman identiteetin key-rakenteella, eikä listaa muunnettaessa synny indeksipohjaista tilaidentiteettiä. Laajennettu arkistolista kootaan yhden Lazy-itemin sisällä; 1 000 kortin suorituskykyä ei ole mitattu, joten sitä ei muutettu ilman todistettua ongelmaa. |
| 277 | Ei vahvistettua vikaa | Painallusanimaatio käyttää animateFloatAsStatea ja komposition elinkaarta. Manuaalista coroutine-silmukkaa tai composition ulkopuolelle jäävää animaatiota ei ole. Ei muutosta. |
| 278 | Ei vahvistettua vikaa | Roomin projektivirrat tuottavat enintään 1 000 projektia järjestettynä indeksoitujen kenttien avulla. Undo-saatavuus kysytään COUNT-kyselyllä eikä koko historiaa ladata. Ei muutosta. |
| 279 | Ei vahvistettua vikaa | BackupCodec lukee enintään 5 MiB + 1 tavua, validoi tiukan UTF-8:n ja rajoittaa projektit 1 000:een. SettingsViewModel sulkee SAF-streamit use-lohkossa eikä säilytä raakaa tiedostosisältöä esikatselun jälkeen. Ei muutosta. |
| 280 | Ei vahvistettua vikaa | AppContainer säilyttää vain Application-kontekstista rakennetut pitkäikäiset riippuvuudet. ViewModelit eivät säilytä Activitya, Viewta, NavControlleria, launcheria tai avointa streamia. Ei muutosta. |
| 281 | Ei vahvistettua vikaa | Lähdemanifestissa ei ole uses-permission-rivejä. Release-merged-manifest lisää AndroidX Startupin oman allekirjoitustasoisen DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION-luvan ja vastaavan uses-permission-rivin; se on sovelluskohtainen sisäinen receiver-suoja, ei ulkoinen laite- tai verkkokyky. INTERNET-, tallennus-, kamera-, mikrofoni- tai ilmoituslupia ei ole. Ei muutosta. |
| 282 | Ei vahvistettua vikaa | allowBackup ja fullBackupContent estävät järjestelmävarmistuksen. Sekä cloud-backup että device-transfer sulkevat pois root-, file-, database-, sharedpref- ja external-alueet. Manuaalinen JSON-vienti jää ainoaksi siirtopoluksi. Ei muutosta. |
| 283 | Ei vahvistettua vikaa | usesCleartextTraffic on false. Release runtime -riippuvuusgraafissa ei ole OkHttpia, Ktoria, Firebasea, analytiikkaa, mainos-, laskutus- tai crash-SDK:ta. DataStoren transitiivinen Okio ei muodosta verkkoyhteyttä. Ei muutosta. |
| 284 | Ei vahvistettua vikaa | Tuotantolähteestä ei löytynyt Log-, println- tai muuta arkaluontoisten projektien tai varmuuskopion sisällön lokitusta. Salaisuuksia, API-avaimia tai verkko-osoitteita ei löytynyt tarkastetusta kokoonpanosta. Ei muutosta. |
| 285 | Ei vahvistettua vikaa | MainActivity suodattaa täysin peitetyt kosketukset ja hylkää myös osittain peitetyn ikkunan MotionEvent-liput. Tuonti validoi koon, tiukan UTF-8:n, skeeman, sovellusidentiteetin, määrän, tunnisteet ja domainrajat ennen esikatselua ja atomista korvausta. Ei muutosta. |
| 286 | Ei vahvistettua vikaa | ProjectValidationTest käyttää tuotannon validointifunktioita ja kattaa Unicode-koodipisteiden 60/61-rajan, tyhjät nimet sekä count-, start-, target- ja repeat-rajat. Ei muutosta. |
| 287 | Ei vahvistettua vikaa | Room-testit käyttävät oikeaa in-memory RowToolDatabasea, ottavat foreign key -säännöt käyttöön ja tarkistavat CASCADE-poiston sekä transaktion rollbackin. Tietokanta suljetaan jokaisessa testissä. Ei muutosta. |
| 288 | Ei vahvistettua vikaa | CounterRepositoryTest kattaa samanaikaiset muutokset, rajojen no-opit, arkistoinnin, undo-kulutuksen ja 100 tapahtuman retentionin oikean Room-tietokannan kautta. Tuotannon Mutex ja transaction-rajat vastaavat testattua sopimusta. Ei muutosta. |
| 289 | Ei vahvistettua vikaa | PreferencesRepository-testit käyttävät testikohtaista DataStore-tiedostoa, peruuttavat scopen ja poistavat tiedoston. Oletukset, tallennus, virhepolut ja tuntemattoman enum-arvon turvallinen fallback on toteutuksessa rajattu. Ei muutosta. |
| 290 | Ei vahvistettua vikaa | BackupCodec- ja BackupRepository-testit kattavat tiukan UTF-8:n, tavupohjaisen 5 MiB rajan, lukemisen pysähtymisen rajalla, duplikaatit, yli 1 000 projektia, domainrajat, onnistuneen korvauksen ja rollbackin. Ei muutosta. |
| 291 | Ei vahvistettua vikaa | Counter-, Projects-, Settings- ja sovellustason ViewModel-polut tarkastettiin lähteestä sekä nykyisistä host- ja Activity-testeistä. Testimäärä ei yksin osoita vikaa, eikä kattamattomasta haarasta löytynyt todistettua virhettä. Ei muutosta. |
| 292 | Ei vahvistettua vikaa | Projects Compose -testit kattavat lataus- ja tyhjätilan, aktiiviset ja arkistoidut projektit, id-pohjaisen valinnan sekä tärkeimmät toiminnot. ProjectEditorDialog-testit kattavat syötteen ja validoinnin. Ei muutosta. |
| 293 | Ei vahvistettua vikaa | Counter Compose -testit kattavat laskurin semantiikan, käytössä/pois käytöstä -tilat, valikon ja tärkeät toiminnot. CountEditorDialogTest kattaa editorin syötteen ja validoinnin. Ei muutosta. |
| 294 | Ei vahvistettua vikaa | MainActivity-testit käyttävät todellista Activitya ja Room-tietokantaa sekä kattavat navigointia ja pysyvyyttä Activityn uudelleenluonnissa. Täyttä prosessikuolemaa tai laiteriippuvaista haptics-kokemusta ne eivät todista; tästä ei johdettu kuvitteellista lähdekoodivikaa. |
| 295 | Osittain varmistettu | assembleDebugAndroidTest käänsi AndroidJUnit4- ja Compose-testit onnistuneesti. adb devices -l ei löytänyt laitetta tai emulaattoria, joten connectedDebugAndroidTest jätettiin ajamatta eikä käännöstä raportoida suorituksena. Ei muutosta. |
| 296 | Osittain varmistettu | Paikallinen JDK 21 -ajo todisti debug-koonnin, host-testit, lintin, KSP:n, Android-testien käännöksen, ktlintin, detekt-raportin ja JaCoCo-raportin. CI-määrittely käyttää vastaavia ydintehtäviä, mutta tässä tarkastuksessa ei väitetä etä-CI-ajon tulosta. Ei muutosta. |
| 297 | Ei vahvistettua vikaa | Nykyinen JaCoCo XML muodostui onnistuneesti. Koko raportin line coverage oli 32,7 % ja branch coverage 26,7 %. Sonar-rajauksen line coverage oli 92,6 % (1 009 katettua, 81 kattamatonta) ja branch coverage 85,1 % (189/33). Rajaus sulkee pois ui/**-, MainActivity- ja RowToolApplication/AppContainer-koodin, joten prosentti ei ole UI-kattavuus. Ei muutosta. |
| 298 | Osittain varmistettu | assembleRelease ja bundleRelease onnistuivat shrink/minify-asetuksilla. APK ja AAB sisälsivät tarkastetut laskurikuvaresurssit, mutta repossa ei ole signingConfigia: APK on nimeltään app-release-unsigned.apk eikä kummassakaan artefaktissa ollut allekirjoitusmerkintöjä. Niitä ei pidetä Play-valmiina. Ei muutosta. |
| 299 | Ei vahvistettua vikaa | PLAY_STORE_LISTING.md, DATA_SAFETY.md, PRIVACY_POLICY.md ja RELEASE_CHECKLIST.md vastaavat tarkastettua maksullista, offline-, paikallisdata- ja manuaalivientimallia sekä lupien puuttumista. Ei muutosta. |
| 300 | Ulkoinen varmistus avoin | Julkaisulista nimeää allekirjoituksen, Play Console -asetukset, tavaramerkkiselvityksen, käännösten ja store-assetien tarkistuksen, oikean laitteen release-testin, hosted privacy URL:n ja rollout-vaiheet ulkoisiksi. Niitä ei voi todistaa lähdekoodin host-ajolla eikä niitä merkitty tehdyiksi. Ei muutosta. |
| 301 | Koko 1–301-portti täyttyy | PROJECT.md luettiin uudelleen ja pysyi muuttumattomana. Promptit 1–301 on käsitelty järjestyksessä viidessä osassa. Osa 4:n auditointi oli valmistunut yhdeksällä korjauksella ja 51 puhtaalla kohdalla, mutta sen tulostiedosto oli jäänyt tallentamatta; rekisteri palautettiin aiemmasta loppuyhteenvedosta ja nykyisen työpuun diffistä. Tämän osan 241–301 kysymyksissä ei vahvistettu uutta koodivikaa. |

## Git-tila ja muutosten erottelu

Tämän osan alkuperäinen baseline ennen promptin 241 tarkastusta oli:

     M app/src/androidTest/java/com/finnvek/rowtool/ui/screens/counter/CounterScreenContentTest.kt
     M app/src/androidTest/java/com/finnvek/rowtool/ui/screens/projects/ProjectEditorDialogTest.kt
     M app/src/androidTest/java/com/finnvek/rowtool/ui/screens/projects/ProjectsScreenContentTest.kt
     M app/src/main/java/com/finnvek/rowtool/RowToolApplication.kt
     M app/src/main/java/com/finnvek/rowtool/data/repository/BackupCodec.kt
     M app/src/main/java/com/finnvek/rowtool/data/repository/BackupRepository.kt
     M app/src/main/java/com/finnvek/rowtool/data/repository/CounterRepository.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/counter/CountEditorDialog.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/counter/CounterScreen.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/counter/CounterViewModel.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectEditorDialog.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectsRoute.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectsScreen.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectsViewModel.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/settings/SettingsViewModel.kt
     M app/src/test/java/com/finnvek/rowtool/data/repository/BackupAndPreferencesRepositoryTest.kt
     M app/src/test/java/com/finnvek/rowtool/data/repository/BackupCodecTest.kt
     M app/src/test/java/com/finnvek/rowtool/data/repository/CounterRepositoryTest.kt
    ?? RowTool-koodintarkistus/RowTool-Codex-review-results-part-02-of-05-prompts-061-120.md
    ?? RowTool-koodintarkistus/RowTool-Codex-review-results-part-03-of-05-prompts-121-180.md
    ?? app/src/androidTest/java/com/finnvek/rowtool/ui/screens/counter/CountEditorDialogTest.kt
    ?? app/src/test/java/com/finnvek/rowtool/ui/screens/counter/CounterViewModelTest.kt
    ?? app/src/test/java/com/finnvek/rowtool/ui/screens/settings/

Koska osa 05 suoritettiin erillisenä liitetyn dokumentin tarkastuksena, kaikki yllä oleva käsiteltiin käyttäjän ennestään olemassa olevana työnä. Auditoinnin dokumentaatiolisäykset ovat promptin 301 vaatima osa-5-tulosrekisteri ja käyttäjän täsmennyksen jälkeen palautettu osa-4-tulosrekisteri.

Tarkastuksen lopullinen git status --short:

     M app/src/androidTest/java/com/finnvek/rowtool/ui/screens/counter/CounterScreenContentTest.kt
     M app/src/androidTest/java/com/finnvek/rowtool/ui/screens/projects/ProjectEditorDialogTest.kt
     M app/src/androidTest/java/com/finnvek/rowtool/ui/screens/projects/ProjectsScreenContentTest.kt
     M app/src/main/java/com/finnvek/rowtool/RowToolApplication.kt
     M app/src/main/java/com/finnvek/rowtool/data/repository/BackupCodec.kt
     M app/src/main/java/com/finnvek/rowtool/data/repository/BackupRepository.kt
     M app/src/main/java/com/finnvek/rowtool/data/repository/CounterRepository.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/counter/CountEditorDialog.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/counter/CounterScreen.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/counter/CounterViewModel.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectEditorDialog.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectsRoute.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectsScreen.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectsViewModel.kt
     M app/src/main/java/com/finnvek/rowtool/ui/screens/settings/SettingsViewModel.kt
     M app/src/test/java/com/finnvek/rowtool/data/repository/BackupAndPreferencesRepositoryTest.kt
     M app/src/test/java/com/finnvek/rowtool/data/repository/BackupCodecTest.kt
     M app/src/test/java/com/finnvek/rowtool/data/repository/CounterRepositoryTest.kt
    ?? RowTool-koodintarkistus/RowTool-Codex-review-results-part-02-of-05-prompts-061-120.md
    ?? RowTool-koodintarkistus/RowTool-Codex-review-results-part-03-of-05-prompts-121-180.md
    ?? RowTool-koodintarkistus/RowTool-Codex-review-results-part-04-of-05-prompts-181-240.md
    ?? RowTool-koodintarkistus/RowTool-Codex-review-results-part-05-of-05-prompts-241-301.md
    ?? app/src/androidTest/java/com/finnvek/rowtool/ui/screens/counter/CountEditorDialogTest.kt
    ?? app/src/test/java/com/finnvek/rowtool/ui/screens/counter/CounterViewModelTest.kt
    ?? app/src/test/java/com/finnvek/rowtool/ui/screens/settings/

## Muutokset

Tässä osassa ei tehty lähdekoodi-, asetus-, testi- tai PROJECT.md-muutoksia, koska prompteissa 241–300 ei vahvistettu korjattavaa vikaa. Uudet dokumentaatiotiedostot ovat tämä tulosraportti ja jälkikäteen palautettu osa-4-tulosrekisteri.

## Suoritettu validointi

- JDK: java version 21.0.12.
- Host- ja staattinen kokonaisajo: .\gradlew.bat :app:assembleDebug :app:testDebugUnitTest :app:lintDebug :app:kspDebugKotlin :app:assembleDebugAndroidTest :app:ktlintCheck :app:detekt :app:createDebugUnitTestCoverageReport --no-daemon --no-configuration-cache --console=plain. Tulos: BUILD SUCCESSFUL; host-suiteissa 10 testiluokkaa, 79 testiä, 0 virhettä, 0 epäonnistumista ja 0 ohitettua.
- Detektin SARIF-raportin results-taulukko oli tyhjä. Pelkkään ignoreFailures-asetuksella onnistuvaan exit-koodiin ei luotettu.
- Android lintissä ei ollut virheitä. Se raportoi viisi saatavilla olevaa vakaata versiopäivitystä; niitä ei tehty tämän koodintarkistuksen sivuvaikutuksena.
- Release: .\gradlew.bat :app:assembleRelease :app:bundleRelease --no-daemon --no-configuration-cache --console=plain. Tulos: BUILD SUCCESSFUL.
- Release-riippuvuudet: .\gradlew.bat :app:dependencies --configuration releaseRuntimeClasspath --no-daemon --no-configuration-cache --console=plain. Tulos: BUILD SUCCESSFUL, eikä graafissa ollut verkkoklientti-, analytiikka-, mainos-, laskutus- tai crash-SDK:ta.
- app-release-unsigned.apk: 3 339 832 tavua, SHA-256 321CF495A9C536833AC4CFF532CDC4641991C097C81C394F13DB4B65921B53FB.
- app-release.aab: 4 506 475 tavua, SHA-256 20D6A1CFC63899A21AF42325148A34A7310F5989CE27E8603E1DD43E6777BF2B.
- adb devices -l ei löytänyt laitetta tai emulaattoria. Android-testit vain käännettiin; niitä ei suoritettu.
- Resurssien kieliavain-, placeholder-, plural- ja kuvahajautukset tarkistettiin erikseen.
- Release-merged-manifest, varmistussäännöt ja julkaisuartefaktien resurssit tarkistettiin muodostetuista tiedostoista.
- git diff --check ja git diff --cached --check ajettiin raportin lisäämisen jälkeen. Molemmat palauttivat exit-koodin 0; Git ilmoitti vain ennestään muokattujen tiedostojen LF–CRLF-normalisointivaroituksia.
- Mikään suoritettu validointi ei epäonnistunut. Lintin viisi versiovaroitusta olivat informatiivisia eivätkä tämän tehtävän aiheuttamia virheitä.

## Aidosti avoimeksi jääneet asiat

- Device- tai emulatoriajo jäi tekemättä, joten fyysinen haptics-tuntuma, valmistajakohtainen haptics-toteutus, todellinen IME/fokus, prosessikuolema ja koko instrumentointisuite eivät ole tässä ajossa vahvistettuja.
- CI-palvelun, Sonar-palvelimen ja Play Consolen nykyistä ulkoista tilaa ei tarkistettu. Allekirjoitus, Play-asetukset, tavaramerkki, store-assetit, hosted privacy URL, release-laitetesti ja rollout ovat edelleen julkaisulistan ulkoisia portteja.
- Laajennetun 1 000 arkistokortin listan suorituskykyä ei profiloitu. Lähdekoodista ei todistettu häiriötä, joten listaa ei refaktoroitu spekulatiivisesti.

Viimeinen käsitelty numero on 301. Promptit 1–301 on käsitelty järjestyksessä ja kaikkien viiden osan tulosrekisterit ovat nyt työtilassa. Osa 4:n tiedosto on palautettu aiemmasta valmistumisraportista; korjauksia ei ajettu tai tehty uudelleen tässä palautuksessa.

Yhtään alkuperäisen baselinen muutosta ei palautettu, ylikirjoitettu, piilotettu, stagettu, commitoitu tai muuten vahingoitettu. Git-historiaa, haaraa, ulkoista tiliä, pilvipalvelua, Play Consolea, allekirjoitusavainta, tuotantodataa, deploymentia, julkaisua tai rolloutia ei muutettu.
