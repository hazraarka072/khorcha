

# Generate a random string for resource uniqueness
/*
resource "random_string" "random_suffix" {
  length  = 5
  special = false
  upper   = false

  lifecycle {
    ignore_changes = all
  }
}
*/

# Upload JAR to S3
resource "aws_s3_object" "lambda_jar" {
  bucket       = var.lambda_bucket_name
  key          = local.lambda_path_in_s3
  source       = var.lambda_artifact
  content_type = "application/java-archive"
  source_hash = filemd5(var.lambda_artifact)
}

# IAM Role for Lambda
resource "aws_iam_role" "lambda_execution_role" {
  name = "kharcha-lambda-role-${var.environment}"

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

  tags = local.tags
}

# IAM Policy for Lambda Role
resource "aws_iam_role_policy" "lambda_execution_policy" {
  name   = "kharcha-lambda-primary-${var.environment}"
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
      },
      {
        Action = [
          "dynamodb:BatchGetItem",
          "dynamodb:BatchWriteItem",
          "dynamodb:PutItem",
          "dynamodb:DescribeTable",
          "dynamodb:ListTables",
          "dynamodb:DeleteItem",
          "dynamodb:GetItem",
          "dynamodb:Scan",
          "dynamodb:Query",
          "dynamodb:UpdateItem",
          "dynamodb:GetRecords"
        ]
        Effect   = "Allow"
        Resource = "*"
      }
    ]
  })
}

# Lambda Function
resource "aws_lambda_function" "micronaut_lambda" {
  function_name = "kharcha-${var.environment}"
  runtime       = "java17"
  handler       = "io.micronaut.function.aws.proxy.payload1.ApiGatewayProxyRequestEventFunction"
  role          = aws_iam_role.lambda_execution_role.arn
  s3_bucket     = var.lambda_bucket_name
  s3_key        = aws_s3_object.lambda_jar.key
  timeout       = var.lambda_timeout
  memory_size   = var.lambda_mem_size

  tags = local.tags

  depends_on = [aws_iam_role_policy.lambda_execution_policy]
}


# API Gateway using Processed Swagger
resource "aws_api_gateway_rest_api" "micronaut_api" {
  name = "micronaut-api-${var.environment}"
  body = local.swagger_body

}

# Deploy API Gateway
resource "aws_api_gateway_deployment" "micronaut_api_deployment" {
  rest_api_id = aws_api_gateway_rest_api.micronaut_api.id
  stage_name  = var.environment


  depends_on = [aws_api_gateway_rest_api.micronaut_api]
}

# Lambda Permission for API Gateway

#output "swagger_body_debug" {
#  value = local.swagger_body
#}


