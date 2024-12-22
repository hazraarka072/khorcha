variable "table_name" {
  description = "The name of the DynamoDB table"
  type        = string
}

variable "hash_key" {
  description = "The primary key (hash key) of the table"
  type        = string
}

variable "hash_key_type" {
  description = "The data type of the hash key (e.g., S for string, N for number)"
  type        = string
  default     = "S"
}

variable "range_key" {
  description = "The optional range key of the table"
  type        = string
  default     = null
}

variable "range_key_type" {
  description = "The data type of the range key (e.g., S for string, N for number)"
  type        = string
  default     = "S"
}

variable "global_secondary_indexes" {
  description = <<EOT
List of maps to define GSIs. Each map supports:
  - name (required): Name of the GSI
  - hash_key (required): Hash key for the GSI
  - hash_key_type (optional, default: S): Type of hash key
  - range_key (optional): Range key for the GSI
  - range_key_type (optional, default: S): Type of range key
  - projection_type (optional, default: ALL): Projection type (ALL, KEYS_ONLY, INCLUDE)
EOT
  type        = list(object({
    name               = string
    hash_key           = string
    hash_key_type      = optional(string, "S")
    range_key          = optional(string, null)
    range_key_type     = optional(string, "S")
    projection_type    = optional(string, "ALL")
  }))
  default = []
}

variable "tags" {
  description = "A map of tags to assign to the table"
  type        = map(string)
  default     = {}
}

variable "deletion_protection" {
  description = "Protest against accidentally deleting a table."
  default = false
  type = bool
}