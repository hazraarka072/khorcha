resource "aws_cognito_user_pool" "pool" {
  count = var.enable_cognito ? 1 : 0
  name = var.name
  username_attributes = ["email",  "phone_number"]

  username_configuration {
    case_sensitive = false
  }

  schema {
    attribute_data_type = "String"
    mutable = true
    name = "name"
    required = true
    string_attribute_constraints {
      min_length = 1
      max_length = 2048
    }
  }
}

# Create Cognito User Pool Client
resource "aws_cognito_user_pool_client" "this" {
  count                     = var.enable_cognito ? 1 : 0
  name                      = var.name
  user_pool_id              = aws_cognito_user_pool.pool[0].id

  generate_secret           = false
  allowed_oauth_flows       = ["code"]
  allowed_oauth_scopes      = ["email", "name", "openid"]
  allowed_oauth_flows_user_pool_client = true
  callback_urls             = var.callback_urls # Replace with your callback URL
  logout_urls               = var.logout_urls   # Replace with your logout URL
}