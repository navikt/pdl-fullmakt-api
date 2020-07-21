# pdl-fullmakt-api

SBS Proxy for [fullmakt](https://github.com/navikt/pdl-fullmakt-ui)


## Deployering

Applikasjonen bygges automatisk til dev / https://www-q0.nav.no/person/pdl-fullmakt-api ved merge til dev/* eller lansere applikasjonen til produksjon / https://www.nav.no/person/pdl-fullmakt-api, knytt en commit til master. 

## Lokalt Kjøring

Erstatt følgende:
* OIDC-TOKEN med cookie "selvbetjening-idtoken" etter å ha logget på https://www-q0.nav.no/person/pdl-fullmakt-ui.
* FNR for pålogget bruker.

## Logging

Feil ved API-kall blir logget via frontendlogger og vises i Kibana<br>
[https://logs.adeo.no](https://logs.adeo.no/app/kibana#)

## Henvendelser

Spørsmål knyttet til koden eller prosjektet kan rettes mot https://github.com/orgs/navikt/teams/pdlfullmakt

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #pdl-fullmakt.

