resource "helm_release" "metrics_server" {
  depends_on = [module.eks]
  chart = "metrics-server"
  name  = "metrics-server"
  repository = "https://kubernetes-sigs.github.io/metrics-server"
  namespace  = "kube-system"
}