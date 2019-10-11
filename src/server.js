const proxy = require("http-proxy-middleware");
const cookies = require("cookie-parser");
const express = require("express");
const decodeJWT = require("jwt-decode");
const app = express();
const port = 8080;
const BASE_URL = "/person/pdl-fullmakt-api";
const VAULT_PATH = "/var/run/secrets/nais.io/vault/environment.env";
const dotenv = require("dotenv").config({ path: VAULT_PATH });
const { setProxyHeaders } = require("./headers");

app.use(cookies());
app.get(`${BASE_URL}/internal/isAlive`, (req, res) => res.sendStatus(200));
app.get(`${BASE_URL}/internal/isReady`, (req, res) => res.sendStatus(200));

app.use(
  proxy(`${BASE_URL}/api`, {
    target: process.env.PDL_FULLMAKT_API_URL,
    pathRewrite: { "^/person/pdl-fullmakt-api/fullmaktsgiver": "" },
    onProxyReq: setProxyHeaders,
    changeOrigin: true
  })
);

app.listen(port, () => console.log(`App listening on port ${port}!`));
