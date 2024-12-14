resource "aws_cognito_user_pool" "pool" {
  name = var.name
  username_attributes = ["email"]

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
  name                      = var.name
  user_pool_id              = aws_cognito_user_pool.pool.id

  generate_secret           = true
  allowed_oauth_flows       = ["code"]
  allowed_oauth_scopes      = ["email", "openid"]
  allowed_oauth_flows_user_pool_client = true
  callback_urls             = var.callback_urls # Replace with your callback URL
  logout_urls               = var.logout_urls   # Replace with your logout URL
}