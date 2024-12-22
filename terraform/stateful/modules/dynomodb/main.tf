resource "aws_dynamodb_table" "this" {
  name                        = var.table_name
  billing_mode                = "PAY_PER_REQUEST" # Always use PAY_PER_REQUEST for Free Tier
  hash_key                    = var.hash_key
  range_key                   = var.range_key
  deletion_protection_enabled = var.deletion_protection

  attribute {
    name = var.hash_key
    type = var.hash_key_type
  }

  # Conditional creation of the range key
  dynamic "attribute" {
    for_each = var.range_key != null ? [var.range_key] : []
    content {
      name = attribute.value
      type = var.range_key_type
    }
  }

  # Global Secondary Indexes (GSIs)
  dynamic "global_secondary_index" {
    for_each = var.global_secondary_indexes
    content {
      name            = lookup(global_secondary_index.value, "name", null)
      hash_key        = lookup(global_secondary_index.value, "hash_key", null)
      range_key       = lookup(global_secondary_index.value, "range_key", null)
      projection_type = lookup(global_secondary_index.value, "projection_type", "ALL")
    }
  }

  tags = var.tags
}
