/*
  Set headers for proxy requests
 */

const setEnheterProxyHeaders = (proxyReq, req, res) => {
  proxyReq.setHeader(
    process.env.PDL_FULLMAKT_API_ENHETERRS_APIKEY_USERNAME,
    process.env.PDL_FULLMAKT_API_ENHETERRS_APIKEY_PASSWORD
  );
};

const setMottakProxyHeaders = (proxyReq, req, res) => {
  const authToken = req.cookies["selvbetjening-idtoken"];

  if (authToken) {
    proxyReq.setHeader("Authorization", `Bearer ${authToken}`);
  }

  proxyReq.setHeader(
    process.env.PDL_FULLMAKT_API_TILBAKEMELDINGSMOTTAK_APIKEY_USERNAME,
    process.env.PDL_FULLMAKT_API_TILBAKEMELDINGSMOTTAK_APIKEY_PASSWORD
  );

  Object.keys(req.headers).forEach(key => {
    proxyReq.setHeader(key, req.headers[key]);
  });
};

module.exports = {
  setEnheterProxyHeaders,
  setMottakProxyHeaders
};
