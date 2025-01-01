locals {
  # Get unique attributes from GSIs
  gsi_attributes = distinct(flatten([
    for idx in var.global_secondary_indexes : [
      {
        name = idx.hash_key
        type = idx.hash_key_type
      },
        idx.range_key != null ? {
        name = idx.range_key
        type = idx.range_key_type
      } : null
    ]
  ]))

  # Remove null values and create final attribute list
  final_attributes = distinct([
    for item in local.gsi_attributes :
    item if item != null
  ])
}