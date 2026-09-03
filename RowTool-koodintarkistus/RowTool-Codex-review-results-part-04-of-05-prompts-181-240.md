# RowTool-koodintarkistuksen tulokset, osa 04/05, promptit 181–240

Tämä tulosrekisteri palautettiin 3.9.2026 aiemman, jo valmistuneen osa-4-auditoinnin loppuyhteenvedosta. Loppuyhteenveto ja nykyinen työpuun diff vahvistavat alla luetellut korjaukset, testit ja validointitulokset. Tässä palautuksessa ei muutettu tuotantokoodia tai testejä uudelleen.

## Promptikohtaiset tulokset

| Prompti | Auditoinnin tulos |
|---:|---|
| 181 | Ei vahvistettua vikaa eikä muutosta. |
| 182 | Ei vahvistettua vikaa eikä muutosta. |
| 183 | Vahvistettu ja korjattu: virheellinen UTF-8 hyväksyttiin korvausmerkein. BackupCodec käyttää nyt tiukkaa UTF-8-dekoodausta ja hylkää CharacterCodingExceptionin; BackupCodecTest todistaa regression. |
| 184 | Ei vahvistettua vikaa eikä muutosta. |
| 185 | Ei vahvistettua vikaa eikä muutosta. |
| 186 | Ei vahvistettua vikaa eikä muutosta. |
| 187 | Ei vahvistettua vikaa eikä muutosta. |
| 188 | Ei vahvistettua vikaa eikä muutosta. |
| 189 | Ei vahvistettua vikaa eikä muutosta. |
| 190 | Ei vahvistettua vikaa eikä muutosta. |
| 191 | Ei vahvistettua vikaa eikä muutosta. |
| 192 | Ei vahvistettua vikaa eikä muutosta. |
| 193 | Ei vahvistettua vikaa eikä muutosta. |
| 194 | Ei vahvistettua vikaa eikä muutosta. |
| 195 | Ei vahvistettua vikaa eikä muutosta. |
| 196 | Ei vahvistettua vikaa eikä muutosta. |
| 197 | Ei vahvistettua vikaa eikä muutosta. |
| 198 | Ei vahvistettua vikaa eikä muutosta. |
| 199 | Ei vahvistettua vikaa eikä muutosta. |
| 200 | Ei vahvistettua vikaa eikä muutosta. |
| 201 | Ei vahvistettua vikaa eikä muutosta. |
| 202 | Ei vahvistettua vikaa eikä muutosta. |
| 203 | Ei vahvistettua vikaa eikä muutosta. |
| 204 | Vahvistettu ja korjattu: tuonnin viimeksi aktiivisen projektin valinta ei käyttänyt samaa järjestystä kuin käynnistys. BackupRepository kysyy nyt valinnan ProjectDaolta onnistuneen transaktion jälkeen; BackupAndPreferencesRepositoryTest todistaa järjestyksen. |
| 205 | Ei vahvistettua vikaa eikä muutosta. |
| 206 | Ei vahvistettua vikaa eikä muutosta. |
| 207 | Ei vahvistettua vikaa eikä muutosta. |
| 208 | Ei vahvistettua vikaa eikä muutosta. |
| 209 | Vahvistettu ja korjattu: rinnakkaisen vanhemman tuontipyynnön tulos saattoi korvata uudemman. SettingsViewModel käyttää pyyntösekvenssiä, jolloin vain uusin pyyntö saa julkaista esikatselun tai virheen; vahvistus on lisäksi suojattu rinnakkaiselta kaksoiskutsulta. SettingsViewModelTest kattaa regressiot. |
| 210 | Ei vahvistettua vikaa eikä muutosta. |
| 211 | Vahvistettu ja korjattu: ensimmäinen lataus näytti hetkellisesti aidolta tyhjältä projektilistalta. ProjectsUiState välittää isLoading-tilan Route- ja Content-tasoille, jotka näyttävät latausindikaattorin. Compose-testit kattavat tilan. |
| 212 | Ei vahvistettua vikaa eikä muutosta. |
| 213 | Vahvistettu ja korjattu yhdessä promptin 211 kanssa: lataus, aito tyhjä tila, aktiiviset ja arkistoidut projektit erotetaan toisistaan. |
| 214 | Vahvistettu ja korjattu: arkistoidut kortit eivät olleet avainnettuja projektin tunnisteella. ProjectsScreen käyttää nyt key(project.id)-avainta; Compose-testi estää tilaidentiteetin siirtymisen uudelleenjärjestyksessä. |
| 215 | Ei vahvistettua vikaa eikä muutosta. |
| 216 | Ei vahvistettua vikaa eikä muutosta. |
| 217 | Ei vahvistettua vikaa eikä muutosta. |
| 218 | Ei vahvistettua vikaa eikä muutosta. |
| 219 | Vahvistettu ja korjattu: ProjectEditorDialog katkaisi tai torjui virheellisen numerosyötteen ennen näkyvää validointia. Kenttä säilyttää nyt käyttäjän tekstin ja näyttää validointivirheen; ProjectEditorDialogTest kattaa regression. |
| 220 | Ei vahvistettua vikaa eikä muutosta. |
| 221 | Ei vahvistettua vikaa eikä muutosta. |
| 222 | Ei vahvistettua vikaa eikä muutosta. |
| 223 | Ei vahvistettua vikaa eikä muutosta. |
| 224 | Ei vahvistettua vikaa eikä muutosta. |
| 225 | Ei vahvistettua vikaa eikä muutosta. |
| 226 | Ei vahvistettua vikaa eikä muutosta. |
| 227 | Ei vahvistettua vikaa eikä muutosta. |
| 228 | Ei vahvistettua vikaa eikä muutosta. |
| 229 | Ei vahvistettua vikaa eikä muutosta. |
| 230 | Ei vahvistettua vikaa eikä muutosta. |
| 231 | Vahvistettu ja korjattu: arkistoidussa projektissa lukema ja valikko olivat edelleen aktivoitavissa. CounterScreen poistaa valikon ja lukeman muokkaustoiminnot käytöstä ja välittää disabled-semanticsin; CounterScreenContentTest kattaa regression. |
| 232 | Vahvistettu ja korjattu: CountEditorDialog torjui liian pitkän tai virheellisen syötteen ja jätti vanhan arvon näkyviin. Kenttä säilyttää nyt syötteen ja validointi estää hyväksymisen; CountEditorDialogTest kattaa regression. |
| 233 | Ei vahvistettua vikaa eikä muutosta. |
| 234 | Ei vahvistettua vikaa eikä muutosta. |
| 235 | Ei vahvistettua vikaa eikä muutosta. |
| 236 | Ei vahvistettua vikaa eikä muutosta. |
| 237 | Ei vahvistettua vikaa eikä muutosta. |
| 238 | Ei vahvistettua vikaa eikä muutosta. |
| 239 | Ei vahvistettua vikaa eikä muutosta. |
| 240 | Ei vahvistettua vikaa eikä muutosta. |

## Korjatut kohdat

Todellinen korjaustarve löytyi yhdeksästä promptista: 183, 204, 209, 211, 213, 214, 219, 231 ja 232. Muutoksia eivät vaatineet promptit 181–182, 184–203, 205–208, 210, 212, 215–218, 220–230 ja 233–240.

Korjauksiin liittyvät 16 tuotanto- ja testitiedostoa:

- app/src/main/java/com/finnvek/rowtool/data/repository/BackupCodec.kt ja app/src/test/java/com/finnvek/rowtool/data/repository/BackupCodecTest.kt: prompti 183.
- app/src/main/java/com/finnvek/rowtool/data/repository/BackupRepository.kt ja app/src/test/java/com/finnvek/rowtool/data/repository/BackupAndPreferencesRepositoryTest.kt: prompti 204.
- app/src/main/java/com/finnvek/rowtool/ui/screens/settings/SettingsViewModel.kt ja app/src/test/java/com/finnvek/rowtool/ui/screens/settings/SettingsViewModelTest.kt: prompti 209.
- app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectsViewModel.kt, ProjectsRoute.kt ja ProjectsScreen.kt sekä app/src/androidTest/java/com/finnvek/rowtool/ui/screens/projects/ProjectsScreenContentTest.kt: promptit 211, 213 ja 214.
- app/src/main/java/com/finnvek/rowtool/ui/screens/projects/ProjectEditorDialog.kt ja sitä vastaava ProjectEditorDialogTest.kt: prompti 219.
- app/src/main/java/com/finnvek/rowtool/ui/screens/counter/CounterScreen.kt ja sitä vastaava CounterScreenContentTest.kt: prompti 231.
- app/src/main/java/com/finnvek/rowtool/ui/screens/counter/CountEditorDialog.kt ja sitä vastaava CountEditorDialogTest.kt: prompti 232.

## Aiemmin raportoitu validointi

- Koko Gradle-validointi läpäisi: build, 79 JVM-testiä, KSP, Android-testisovelluksen kokoaminen, ktlint, detekt ja lint.
- JVM-testit: 79/79 läpäisi.
- Detekt: 0 löydöstä.
- Android Lint: 0 virhettä ja viisi tehtävän ulkopuolista versiopäivitysvaroitusta.
- git diff --check läpäisi.
- Instrumentoidut Compose-regressiotestit kääntyivät ja paketoituivat, mutta niitä ei suoritettu, koska liitettyä Android-laitetta tai emulaattoria ei ollut.
- Aiemmat keskeneräiset muutokset säilytettiin. Commitia, pushia tai pull requestia ei tehty.

Osa 5:n lopullinen validointi suoritti saman nykyisen koodin host-, staattiset ja release-portit uudelleen onnistuneesti. Tämä palautus lisäsi vain puuttuneen tulosrekisterin.
