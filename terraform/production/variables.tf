# Provider 
variable "region" {
  type = string
}

variable "access_key" {
  type = string
}

variable "secret_key" {
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
variable "argocd_server" {
  type = string
}

variable "argocd_username" {
  type = string
}

variable "argocd_password" {
  type = string
}