resource "helm_release" "kube-ops-view" {
  depends_on = [module.eks]
  name       = "kube-ops-view"
  repository = "https://charts.helm.sh/stable"
  chart      = "kube-ops-view"
  namespace  = "kube-system"

  set {
    name  = "service.type"
    value = "LoadBalancer"
  }

  set {
    name  = "rbac.create"
    value = "true"
  }
}