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