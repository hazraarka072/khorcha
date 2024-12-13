variable "environment" {}
variable "swagger_body" {}
variable "name" {}
variable "enable_cognito" {
  type = bool
  default = false
}
variable "callback_urls" {
  type = list(string)
  default = []
}
variable "logout_urls" {
  type = list(string)
  default = []
}