

# Generate a random string for resource uniqueness
resource "random_string" "random_suffix" {
  length  = 5
  special = false
  upper   = false

  lifecycle {
    ignore_changes = all
  }
}

# Upload JAR to S3
resource "aws_s3_object" "lambda_jar" {
  bucket       = var.lambda_bucket_name
  key          = "my-lambda-${random_string.random_suffix.result}.jar"
  source       = "../build/libs/khorcha-0.1-all.jar"
  content_type = "application/java-archive"
}

# IAM Role for Lambda
resource "aws_iam_role" "lambda_execution_role" {
  name = "lambda-execution-role-${random_string.random_suffix.result}"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action    = "sts:AssumeRole",
        Effect    = "Allow",
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })

  tags = {
    Owner    = var.Owner
    Instance = random_string.random_suffix.result
  }
}

# IAM Policy for Lambda Role
resource "aws_iam_role_policy" "lambda_execution_policy" {
  name   = "lambda-policy-${random_string.random_suffix.result}"
  role   = aws_iam_role.lambda_execution_role.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ]
        Effect   = "Allow"
        Resource = "arn:aws:logs:*:*:*"
      },
      {
        Action = "s3:GetObject"
        Effect = "Allow"
        Resource = "arn:aws:s3:::${var.lambda_bucket_name}/*"
      }
    ]
  })
}

# Lambda Function
resource "aws_lambda_function" "micronaut_lambda" {
  function_name = "micronaut-lambda-${random_string.random_suffix.result}"
  runtime       = "java17"
  handler       = "io.micronaut.function.aws.proxy.payload1.ApiGatewayProxyRequestEventFunction"
  role          = aws_iam_role.lambda_execution_role.arn
  s3_bucket     = var.lambda_bucket_name
  s3_key        = aws_s3_object.lambda_jar.key


  tags = {
    Owner    = var.Owner
    Instance = random_string.random_suffix.result
  }

  depends_on = [aws_iam_role_policy.lambda_execution_policy]
}

locals {
  swagger_body = replace(file("../swagger.json"), "lambda_function_arn1", aws_lambda_function.micronaut_lambda.arn)
}


# API Gateway using Processed Swagger
resource "aws_api_gateway_rest_api" "micronaut_api" {
  name = "micronaut-api-${random_string.random_suffix.result}"
  body = local.swagger_body

}

# Deploy API Gateway
resource "aws_api_gateway_deployment" "micronaut_api_deployment" {
  rest_api_id = aws_api_gateway_rest_api.micronaut_api.id
  stage_name  = "prod"


  depends_on = [aws_api_gateway_rest_api.micronaut_api]
}

# Lambda Permission for API Gateway

#output "swagger_body_debug" {
#  value = local.swagger_body
#}


