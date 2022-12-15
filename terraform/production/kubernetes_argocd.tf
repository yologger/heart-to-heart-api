resource "kubernetes_namespace" "argocd" {
  depends_on = [module.eks]
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