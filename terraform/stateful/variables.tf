variable "aws_region" {
  default = ""
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