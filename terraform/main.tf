

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

resource "null_resource" "download_lambda_jar" {
  triggers = {
    on_version_change = var.release
  }

  provisioner "local-exec" {
    command = "curl -o lambda.jar ${local.lambda_remote_url}"
  }

}

# Upload JAR to S3
resource "aws_s3_object" "lambda_jar" {
  bucket       = var.lambda_bucket_name
  key          = "my-lambda-${var.environment}.jar"
  source       = "./lambda.jar"
  content_type = "application/java-archive"
  depends_on = [null_resource.download_lambda_jar]
}

# IAM Role for Lambda
resource "aws_iam_role" "lambda_execution_role" {
  name = "lambda-execution-role-${var.environment}"

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
  name   = "lambda-policy-${var.environment}"
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
  function_name = "micronaut-lambda-${var.environment}"
  runtime       = "java17"
  handler       = "io.micronaut.function.aws.proxy.payload1.ApiGatewayProxyRequestEventFunction"
  role          = aws_iam_role.lambda_execution_role.arn
  s3_bucket     = var.lambda_bucket_name
  s3_key        = aws_s3_object.lambda_jar.key


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


