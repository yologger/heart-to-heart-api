# IAM USER
resource "aws_iam_user" "github_actions" {
  name = "github-actions-uploader"
}

# Access Key for IAM USER
resource "aws_iam_access_key" "github_actions_uploader_access_key" {
  user = aws_iam_user.github_actions.name
}

# Policy for IAM USER
data "aws_iam_policy" "amazon_S3_full_access" {
  arn  = "arn:aws:iam::aws:policy/AmazonS3FullAccess"
}
resource "aws_iam_user_policy_attachment" "amazon_S3_full_access_attach" {
  user       = aws_iam_user.github_actions.name
  policy_arn = data.aws_iam_policy.amazon_S3_full_access.arn
}

## AWS S3 Bucket
resource "aws_s3_bucket" "h2h-api-build-result" {
  bucket = "h2h-api-build-result"
}

## S3 Bucket Public Access Block
resource "aws_s3_bucket_public_access_block" "aws_s3_bucket_public_access_block" {
  bucket = aws_s3_bucket.h2h-api-build-result.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

## IAM USER Access Key
output "access_key" {
  value = aws_iam_access_key.github_actions_uploader_access_key.id
}

## IAM USER Secret Key
output "secret_key" {
  value = nonsensitive(aws_iam_access_key.github_actions_uploader_access_key.secret)
}