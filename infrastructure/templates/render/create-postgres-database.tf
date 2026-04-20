resource "render_postgres" "postgres_instance" {
  name = var.postgres_instance_name
  plan = "basic_256mb"
  region = "ohio"
  version = "18"
  database_name = var.postgres_database_name
  database_user = var.postgres_user
  high_availability_enabled = false
}
