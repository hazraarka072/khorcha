variable "Owner" {
  description = "The owner of the resources"
  type        = string
  default     = "arka"
}

variable "aws_region" {
  description = "AWS region for resource deployment"
  type        = string
  default     = "us-east-1" # Adjust if needed
}
variable "lambda_bucket_name" {
  description = "Name of the existing S3 bucket to use for Lambda JAR"
  type        = string
  default     = "kharcha-tf-state-bucket" # Adjust if needed

}
variable "release" {
  description = "Lambda release number in github"
  type = number
  default = 7
}
variable "lambda_artifact_name" {
  description = "Lambda artifact name in github"
  type = string
  default = "khorcha-0.1-all.jar"
}