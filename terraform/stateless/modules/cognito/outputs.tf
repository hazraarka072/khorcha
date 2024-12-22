output "cognito_user_pool_arn" {
  value = aws_cognito_user_pool.pool.arn
}

output "jwks_url" {
  value = "https://cognito-idp.${data.aws_region.current.name}.amazonaws.com/${aws_cognito_user_pool.pool.id}/.well-known/jwks.json"
}
