provider "aws" {
  region = var.aws_region
}
terraform {
  required_version = ">= 1.10.0"
  backend "s3" {
    bucket         = "kharcha-tf-state-bucket"
    key            = "state/khorcha-dev-arka3.tfstate"
    region         = "us-east-1"
    dynamodb_table = "kharcha-tf-locks"
    encrypt        = true
  }
}