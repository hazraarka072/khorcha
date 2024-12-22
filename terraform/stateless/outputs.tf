output "lambda_function_arn" {
  value = module.kharcha_lambda.lambda_function_arn
}

output "api_gateway_url" {
  value = module.kharcha_api.api_gateway_url
}
