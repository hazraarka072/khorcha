output "lambda_function_arn" {
  value = aws_lambda_function.micronaut_lambda.arn
}

output "api_gateway_url" {
  value = aws_api_gateway_deployment.micronaut_api_deployment.invoke_url
}
