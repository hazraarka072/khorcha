

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

module "kharcha_lambda" {
  source = "./modules/lambda"
  lambda_bucket_name = var.lambda_bucket_name
  lambda_artifact = var.lambda_artifact
  lambda_timeout = var.lambda_timeout
  environment = var.environment
  lambda_mem_size = var.lambda_mem_size
  lambda_path_in_s3 = local.lambda_path_in_s3
  role = aws_iam_role.lambda_execution_role.arn
  tags = local.tags

  depends_on = [aws_iam_role_policy.lambda_execution_policy]
}

module "kharcha_api" {
  source = "./modules/api-gateway"
  swagger_body = local.swagger_body
  environment = var.environment
}

# Lambda Permission for API Gateway

#output "swagger_body_debug" {
#  value = local.swagger_body
#}


