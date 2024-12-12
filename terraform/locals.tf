locals {
  lambda_remote_url = "https://github.com/hazraarka072/khorcha/releases/download/${var.release}/${var.lambda_artifact_name}"
  swagger_body = replace(file("../swagger.json"), "lambda_function_arn1", aws_lambda_function.micronaut_lambda.arn)
  tags = {
    Owner    = var.Owner
    Environment = var.environment
  }
}