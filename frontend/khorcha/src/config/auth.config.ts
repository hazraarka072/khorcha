export const authConfig = {
  authority: import.meta.env.VITE_OIDC_ISSUER,
  client_id: import.meta.env.VITE_OIDC_CLIENT_ID,
  client_secret: import.meta.env.VITE_OIDC_CLIENT_SECRET,
  redirect_uri: import.meta.env.VITE_REDIRECT_URI,
  response_type: 'code',
  scope: 'openid email',
  automaticSilentRenew: true,

/*
    metadata: {
      issuer: import.meta.env.VITE_OIDC_ISSUER,
      authorization_endpoint: `${import.meta.env.VITE_OIDC_ISSUER}/oauth2/authorize`,
      token_endpoint: `${import.meta.env.VITE_OIDC_ISSUER}/oauth2/token`,
      userinfo_endpoint: `${import.meta.env.VITE_OIDC_ISSUER}/oauth2/userInfo`,
      end_session_endpoint: `${import.meta.env.VITE_OIDC_ISSUER}/logout`,
    },
*/
    // Add token endpoint authentication method
    tokenEndpointAuthMethod: 'client_secret_post',
};