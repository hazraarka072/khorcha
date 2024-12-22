variable "aws_region" {
  description = "AWS region for resource deployment"
  type        = string
  default     = "us-east-1" # Adjust if needed
}

variable "Owner" {
  description = "The owner of the resources"
  type        = string
  default     = "arka"
}

variable "environment" {
  description = "Environment to deploy the components for"
  type = string
  default = "dev"
}