module "eks" {
  source = "terraform-aws-modules/eks/aws"

  cluster_name                    = var.cluster_name
  cluster_version                 = var.cluster_version
  cluster_endpoint_private_access = false
  cluster_endpoint_public_access  = true

  cluster_addons = {
    coredns = {
      resolve_conflicts = "OVERWRITE"
    }
    kube-proxy = {}
    vpc-cni    = {
      resolve_conflicts = "OVERWRITE"
    }
  }

  vpc_id     = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets

  // EC2 Computing
  eks_managed_node_group_defaults = {
    disk_size      = 20
    instance_types = ["t3.large"]
  }

  // Cluster Auto Scaling
  eks_managed_node_groups = {
    ("${var.cluster_name}-node-group") = {
      ## Cluster Auto Scaler Group
      min_size       = 1  ## 최소
      max_size       = 5  ## 최대
      desired_size   = 2  ## 기본
      instance_types = ["t3.xlarge"]
      capacity_type  = "SPOT"
      update_config  = {
        max_unavailable_percentage = 50
      }
      tags = {
        "k8s.io/cluster-autoscaler/${var.cluster_name}" : "owned"
        "k8s.io/cluster-autoscaler/enabled" : "true"
      }
    }
  }

  // Security Group applied to each worker node
  node_security_group_additional_rules = {
    // Metrics Server API를 위한 Ingress 허용
    ingress_metrics_server = {
      type        = "ingress"
      protocol    = "-1"
      from_port   = 10250
      to_port     = 10250
      cidr_blocks = ["0.0.0.0/0"]
      description = "Ingress for metrics server"
    }
  }
}