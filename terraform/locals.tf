locals {
  swagger_body = templatefile("../swagger.json.tpl", {
    lambda_function_arn = module.kharcha_lambda.lambda_function_arn
  })

  tags = {
    Owner    = var.Owner
    Environment = var.environment
  }

  lambda_path_in_s3 = "artifact/kharcha-${var.environment}.jar"
}