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
      min_size       = 1  ## 최소
      max_size       = 10 ## 최대
      desired_size   = 1  ## 기본
      instance_types = ["t3.xlarge"]
      capacity_type  = "SPOT"
    }
  }

  // Security Group applied to each worker node
  node_security_group_additional_rules = {
    #    // Node Group에 AWS Load Balancer Controller를 위한 Security Group 추가
    #    ingress_allow_access_from_control_plane = {
    #      type                          = "ingress"
    #      protocol                      = "tcp"
    #      from_port                     = 9443
    #      to_port                       = 9443
    #      source_cluster_security_group = true
    #      description                   = "Allow access from control plane to webhook port of AWS load balancer controller"
    #    },
    #    // Metrics API와 Node Group간 통신
    ingress_metrics_server = {
      type                          = "ingress"
      protocol                      = "-1"
      from_port                     = 10250
      to_port                       = 10250
      source_cluster_security_group = true
      description                   = "Ingress for metrics server"
    }
    #    allow_all_outbound_traffic = {
    #      type                          = "egress"
    #      protocol                      = "-1"
    #      from_port                     = 0
    #      to_port                       = 0
    #      # source_cluster_security_group = true
    #      cidr_blocks = ["0.0.0.0/0"]
    #      description                   = "Allow all outbound traffic"
    #    }
  }
}