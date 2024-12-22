module "dynamodb_table" {
  source = "modules/dynomodb"

  table_name    = "accounts-${var.environment}"
  hash_key      = "user"
  hash_key_type = "S"

  # range_key      = ""
  # range_key_type = "S"

  deletion_protection = true

  tags = local.tags
}
