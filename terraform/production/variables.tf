# AWS VPC
variable "region" {
  type = string
}
variable "access_key" {
  type = string
}
variable "secret_key" {
  type = string
}

# AWS EKS
variable "cluster_name" {
  type = string
}
variable "cluster_version" {
  type = string
}

# AWS RDS
variable "rds_username" {
  type = string
}
variable "rds_password" {
  type = string
}

# Argo CD
variable "argocd_password" {
  type = string
}

# Grafana
variable "grafana_password" {
  type = string
}