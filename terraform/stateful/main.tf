module "accounts_dynamodb_table" {
  source = "./modules/dynomodb"

  table_name    = "accounts-${var.environment}"
  hash_key      = "email"
  hash_key_type = "S"

  range_key      = "accountName"
  range_key_type = "S"

  deletion_protection = true

  tags = local.tags
}

module "transactions_dynamodb_table" {
  source = "./modules/dynomodb"

  table_name    = "transactions-${var.environment}"
  hash_key      = "id"
  hash_key_type = "S"

  range_key      = "time"
  range_key_type = "S"

  deletion_protection = true

  global_secondary_indexes = [
    {
      name     = "transactionType"
      hash_key = "transactionType"
    }
  ]

  tags = local.tags
}
