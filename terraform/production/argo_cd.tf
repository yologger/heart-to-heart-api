resource "kubernetes_namespace" "argocd" {
  metadata {
    name = "argocd"
  }
}

resource "helm_release" "argocd" {
  depends_on = [kubernetes_namespace.argocd]
  name       = "argocd"
  repository = "https://argoproj.github.io/argo-helm"
  chart      = "argo-cd"
  namespace  = "argocd"
  version    = "4.9.0"

  set {
    name  = "server.service.type"
    value = "LoadBalancer"
  }
}

terraform {
  required_providers {
    argocd = {
      source = "oboukili/argocd"
      version = "0.4.7"
    }
  }
}

provider "argocd" {
  server_addr = var.argocd_server
  username = var.argocd_username
  password = var.argocd_password
}