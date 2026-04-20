output "render_postgresql_connection_string" {
  description = "Render PostgreSQL connection string"
  value = nonsensitive(render_postgres.postgres_instance.connection_info.internal_connection_string)
}
