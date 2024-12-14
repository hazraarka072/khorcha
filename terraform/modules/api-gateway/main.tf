# API Gateway using Processed Swagger
resource "aws_api_gateway_rest_api" "micronaut_api" {
  name = var.name
  body = var.swagger_body

}

# Deploy API Gateway
resource "aws_api_gateway_deployment" "micronaut_api_deployment" {
  rest_api_id = aws_api_gateway_rest_api.micronaut_api.id
  stage_name  = var.environment
}

resource "aws_api_gateway_authorizer" "cognito" {
  count = var.cognito_user_pool_arn == "" ? 1 : 0
  name          = var.name
  type          = "COGNITO_USER_POOLS"
  rest_api_id   = aws_api_gateway_rest_api.micronaut_api.id
  provider_arns = [var.cognito_user_pool_arn]
}