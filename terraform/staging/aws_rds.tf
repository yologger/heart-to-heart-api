resource "aws_security_group" "rds_security_group" {
  name        = "rds_security_group"
  description = "rds_security_group"

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["121.170.140.77/32"]
  }

  ingress {
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [aws_security_group.ec2_security_group.id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_db_parameter_group" "rds_parameter_group" {
  name   = "rds-parameter-group"
  family = "mariadb10.3"

  parameter {
    name  = "character_set_client"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_connection"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_database"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_filesystem"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_results"
    value = "utf8mb4"
  }

  parameter {
    name  = "character_set_server"
    value = "utf8mb4"
  }

  parameter {
    name  = "collation_connection"
    value = "utf8mb4_general_ci"
  }

  parameter {
    name  = "collation_server"
    value = "utf8mb4_general_ci"
  }
}

resource "aws_db_instance" "rds_db" {
  identifier             = "h2h-db"
  db_name                = "h2h_db"
  allocated_storage      = 20
  engine                 = "mariadb"
  engine_version         = "10.3.37"
  instance_class         = "db.t3.micro"
  username               = var.rds_username
  password               = var.rds_password
  multi_az               = false
  skip_final_snapshot    = true
  publicly_accessible    = true
  parameter_group_name   = aws_db_parameter_group.rds_parameter_group.name
  vpc_security_group_ids = [aws_security_group.rds_security_group.id]
}