# API Gateway using Processed Swagger
resource "aws_api_gateway_rest_api" "micronaut_api" {
  name = "micronaut-api-${var.environment}"
  body = var.swagger_body

}

# Deploy API Gateway
resource "aws_api_gateway_deployment" "micronaut_api_deployment" {
  rest_api_id = aws_api_gateway_rest_api.micronaut_api.id
  stage_name  = var.environment
}