## IAM USER
data "aws_iam_policy" "aws_code_deploy_full_access" {
  arn = "arn:aws:iam::aws:policy/AWSCodeDeployFullAccess"
}
resource "aws_iam_user_policy_attachment" "aws_code_deploy_full_access_attach" {
  user       = aws_iam_user.github_actions.name
  policy_arn = data.aws_iam_policy.aws_code_deploy_full_access.arn
}

## IAM ROLE for EC2
resource "aws_iam_role" "ec2_access_code_deploy_role" {
  name               = "ec2_access_code_deploy_role"
  assume_role_policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Action" : [
          "sts:AssumeRole"
        ],
        "Principal" : {
          "Service" : [
            "ec2.amazonaws.com"
          ]
        }
      }
    ]
  })
}
data "aws_iam_policy" "amazon_ec2_role_for_aws_code_deploy_policy" {
  arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2RoleforAWSCodeDeploy"
}
resource "aws_iam_role_policy_attachment" "ec2_access_code_deploy_role_policy_attach" {
  role       = aws_iam_role.ec2_access_code_deploy_role.name
  policy_arn = data.aws_iam_policy.amazon_ec2_role_for_aws_code_deploy_policy.arn
}
resource "aws_iam_instance_profile" "ec2_profile" {
  name = "ec2_profile"
  role = aws_iam_role.ec2_access_code_deploy_role.name
}

## IAM Role for CodeDeploy
resource "aws_iam_role" "code_deploy_role" {
  name               = "code_deploy_role"
  assume_role_policy = jsonencode({
    "Version": "2012-10-17",
    "Statement": [
      {
        "Sid": "",
        "Effect": "Allow",
        "Principal": {
          "Service": [
            "codedeploy.amazonaws.com"
          ]
        },
        "Action": [
          "sts:AssumeRole"
        ]
      }
    ]
  })
}
data "aws_iam_policy" "code_deploy_policy" {
  arn = "arn:aws:iam::aws:policy/service-role/AWSCodeDeployRole"
}
resource "aws_iam_role_policy_attachment" "code_deploy_role_policy_attach" {
  role       = aws_iam_role.code_deploy_role.name
  policy_arn = data.aws_iam_policy.code_deploy_policy.arn
}

resource "aws_codedeploy_app" "code_deploy" {
  name = "h2h-api-deployer"
}

resource "aws_codedeploy_deployment_group" "deployment_group" {
  app_name              = aws_codedeploy_app.code_deploy.name
  deployment_group_name = "h2h-api-deploy-group"
  service_role_arn      = aws_iam_role.code_deploy_role.arn

  deployment_style {
    deployment_type   = "IN_PLACE"
  }

  ec2_tag_set {
    ec2_tag_filter {
      key   = "name"
      type  = "KEY_AND_VALUE"
      value = "h2h-api"
    }
  }
}