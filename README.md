[![CircleCI](https://circleci.com/gh/navikt/pdl-fullmakt-api/tree/master.svg?style=svg&circle-token=42f13ff89701ab775c8daecddc815955cebe5820)](https://circleci.com/gh/navikt/pdl-fullmakt-api/tree/master)

# pdl-fullmakt-api

SBS Proxy for [fullmakt](https://github.com/navikt/pdl-fullmakt-ui)


## Deployering

Applikasjonen bygges automatisk til dev / https://www-q0.nav.no/person/pdl-fullmakt-api ved merge til master eller ved manuell godkjenning i [CircleCI](https://circleci.com/gh/navikt/workflows/pdl-fullmakt-api). <br><br>
For å lansere applikasjonen til produksjon / https://www.nav.no/person/pdl-fullmakt-api, knytt en commit til en [Git tag](https://git-scm.com/book/en/v2/Git-Basics-Tagging):

```
git tag -a vX.X.X -m "Din melding"
```

Push den nye versjonen til GitHub og merge til master.

```
git push --tags
```

Godkjenn produksjonssettingen i [CircleCI](https://circleci.com/gh/navikt/workflows/pdl-fullmakt-api).

## Lokalt Kjøring


Erstatt følgende:
* OIDC-TOKEN med cookie "selvbetjening-idtoken" etter å ha logget på https://www-q0.nav.no/person/pdl-fullmakt-ui.
* FNR for pålogget bruker.
* STS-TOKEN for systembruker. Kan hentes ut via kall:

```
curl --insecure --user srvpersonopplysnin:PASSORD "https://security-token-service.nais.preprod.local/rest/v1/sts/token?grant_type=client_credentials&scope=openid" --header "accept: application/json"
```

* PASSORD slås opp i vault.


## Logging

Feil ved API-kall blir logget via frontendlogger og vises i Kibana<br>
[https://logs.adeo.no](https://logs.adeo.no/app/kibana#)

## Henvendelser

Spørsmål knyttet til koden eller prosjektet kan rettes mot https://github.com/orgs/navikt/teams/pdlfullmakt

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #pdl-fullmakt.

