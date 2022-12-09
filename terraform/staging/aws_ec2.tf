resource "aws_security_group" "security_group" {
  name = "security_group"
  description = "Allow SSH port from all"
  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["121.170.140.77/32"]
  }
  ingress {
    from_port = 80
    to_port = 80
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port = 443
    to_port = 443
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port = 0
    to_port   = 0
    protocol  = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "tls_private_key" "private_key" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "key_pair" {
  key_name   = "key_pair"
  public_key = tls_private_key.private_key.public_key_openssh

  provisioner "local-exec" {
    command = "echo '${tls_private_key.private_key.private_key_pem}' > ./../../aws/staging/keypair-ec2.pem"
  }
}

resource "aws_instance" "h2h-api-ec2" {
  ami = "ami-0eddbd81024d3fbdd"
  instance_type = "t2.micro"
  key_name = aws_key_pair.key_pair.key_name
  vpc_security_group_ids = [
    aws_security_group.security_group.id,
  ]
  root_block_device {
    volume_size = 30
  }
}

resource "aws_eip" "elastic_ip" {
  instance = aws_instance.h2h-api-ec2.id
  vpc      = true
}