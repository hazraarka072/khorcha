output "lambda_function_arn" {
  value = module.kharcha_lambda.lambda_function_arn
}

output "api_gateway_url" {
  value = module.kharcha_api.api_gateway_url
}

output "congnito_user_pool_domain" {
  value = var.enable_cognito ? module.kharcha_cognito[0].congnito_user_pool_domain : ""
}
