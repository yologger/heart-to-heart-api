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
}

resource "kubernetes_ingress" "argocd-ingress" {
  wait_for_load_balancer = true
  metadata {
    name = "h2h-api-ingress"
    namespace = "argocd"
    annotations = {
      "alb.ingress.kubernetes.io/healthcheck-path": "/"
      "alb.ingress.kubernetes.io/healthcheck-protocol": "HTTP"
      "alb.ingress.kubernetes.io/scheme": "internet-facing"
      "alb.ingress.kubernetes.io/target-type": "ip"
    }
  }
  spec {
    ingress_class_name = "alb"
    rule {
      http {
        path {
          path = "/"
          backend {
            service_name = "argocd-server"
            service_port = "80"
          }
        }
      }
    }
  }
}