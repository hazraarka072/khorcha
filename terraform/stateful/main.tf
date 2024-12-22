module "dynamodb_table" {
  source = "modules/dynomodb"

  table_name    = "accounts-${var.environment}"
  hash_key      = "email"
  hash_key_type = "S"

  range_key      = "accountName"
  range_key_type = "S"

  deletion_protection = true

  tags = local.tags
}
