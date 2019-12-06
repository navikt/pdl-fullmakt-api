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
const { getStsToken } = require("./ststoken");

app.use(cookies());
app.get(`${BASE_URL}/internal/isAlive`, (req, res) => res.sendStatus(200));
app.get(`${BASE_URL}/internal/isReady`, (req, res) => res.sendStatus(200));
app.get(`${BASE_URL}/fodselsnr`, (req, res) =>
    res.send({ fodselsnr: decodeJWT(req.cookies["selvbetjening-idtoken"]).sub })
);

app.use(
  getStsToken(`${BASE_URL}`),
  proxy(`${BASE_URL}`, {
    target: process.env.PDL_FULLMAKT_API_URL,
    pathRewrite: { "^/person/pdl-fullmakt-api": "" },
    onProxyReq: setProxyHeaders,
    changeOrigin: true,
    proxyErrorHandler: function(err, res, next) {
      console.error("Error in pdl-fullmakt-api proxy", err)
      next(err);
    }
  })
);

app.listen(port, () => console.log(`App listening on port ${port}!`));
