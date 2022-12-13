resource "helm_release" "metrics_server" {
  chart = "metrics-server"
  name  = "metrics-server"
  repository = "https://kubernetes-sigs.github.io/metrics-server"
  namespace  = "kube-system"

  set {
    name  = "kubelet-insecure-tls"
    value = ""
  }
}