variable "postgres_database_name" {
  description = "Name of the PostgreSQL database"
  type = string
  nullable = false
  sensitive = true
}

variable "postgres_instance_name" {
  description = "Name of the PostgreSQL instance"
  type = string
  nullable = false
  sensitive = false
}

variable "postgres_user" {
  description = "Name of the PostgreSQL user"
  type = string
  nullable = false
  sensitive = true
}

variable "web_service_name" {
  description = "Name of the web service"
  type = string
  nullable = false
  sensitive = false
}
