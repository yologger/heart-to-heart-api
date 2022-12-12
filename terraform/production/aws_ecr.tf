locals {
  app_name = "heart-to-heart-api"
}

## ECR Repository
resource "aws_ecr_repository" "ecr_repository" {
  name = local.app_name
}