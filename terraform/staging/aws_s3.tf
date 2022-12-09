resource "aws_iam_user" "github_actions_uploader" {
  name = "github-actions-uploader"
}

resource "aws_iam_access_key" "github_actions_uploader_access_key" {
  user = aws_iam_user.github_actions_uploader.name
}

output "access_key" {
  value = aws_iam_access_key.github_actions_uploader_access_key.id
}

output "secret_key" {
  value = nonsensitive(aws_iam_access_key.github_actions_uploader_access_key.secret)
}

resource "aws_iam_user_policy" "github_actions_uploader_policy" {
  name = "github_actions_uploader_policy"
  user = aws_iam_user.github_actions_uploader.name

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": ["s3:ListBucket"],
      "Resource": ["arn:aws:s3:::h2h-api-build-result"]
    },
    {
      "Effect": "Allow",
      "Action": [
        "s3:PutObject",
        "s3:GetObject",
        "s3:DeleteObject"
      ],
      "Resource": ["arn:aws:s3:::test/*"]
    }
  ]
}
EOF
}

resource "aws_s3_bucket" "h2h-api-build-result" {
  bucket = "h2h-api-build-result"
  force_destroy = true
}