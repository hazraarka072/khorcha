locals {
  swagger_body = replace(file("../swagger.json"), "lambda_function_arn1", module.kharcha_lambda.lambda_function_arn)
  tags = {
    Owner    = var.Owner
    Environment = var.environment
  }

  lambda_path_in_s3 = "artifact/kharcha-${var.environment}.jar"
}